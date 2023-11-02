package com.example.expenseapp.ui.Activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseapp.R
import com.example.expenseapp.ViewModel.TransactionViewModel
import com.example.expenseapp.databinding.ActivityMainBinding
import com.example.expenseapp.helpers.Calculate
import com.example.expenseapp.helpers.ConvertDate
import com.example.expenseapp.ui.Adapters.ShowEmptyAdapter
import com.example.expenseapp.ui.Adapters.TransactionsAdapter
import com.example.expenseapp.ui.Fragments.AddTransactionFragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.util.Calendar

/**
 * BUGS
 *
 * Year sakepachi new months ko dehkhaunu parchs, but not it shows of the previous year months also (not yesr specific)
 *
 *
 * REMAINING
 *
 * Month picker on date click
 */

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var transactionsRecycler: RecyclerView
    lateinit var viewModel: TransactionViewModel

    private val auth = FirebaseAuth.getInstance()
    lateinit var previousDate: String

    lateinit var tabs: TabLayout

    var showCalander = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = "Transactions"
        window.statusBarColor = getColor(R.color.orange)

        viewModel = TransactionViewModel(application)

        initUI()

    }

    fun initUI() {
        var addTransactionSheet = AddTransactionFragment()
        tabs = binding.selectTabs

        binding.mainDate.text = ConvertDate.formatDate(LocalDate.now())

        val daily = binding.selectTabs.getTabAt(0)!!
        val monthly = binding.selectTabs.getTabAt(1)!!
        val summary = binding.selectTabs.getTabAt(2)!!

        val allButtons = listOf(
            binding.backButton,
            binding.forwardButton,
            binding.logOut,
            binding.addTransaction,
            binding.mainDate,
            monthly.view,
            daily.view,
        )

        allButtons.forEach { button ->
            button.setOnClickListener {
                when (button) {
                    binding.backButton -> subtract()
                    binding.forwardButton -> add()
                    binding.logOut -> logOut()
                    binding.addTransaction -> addTransactionSheet.show(supportFragmentManager, "Hi")
                    monthly.view -> pressMonthlyTab()
                    daily.view -> pressDailyTab()
                    binding.mainDate -> showCalender()
                }
            }
        }

        transactionsRecycler = binding.recyclerView
        transactionsRecycler.layoutManager = GridLayoutManager(this, 1)

        showTransactions()
    }

    fun showCalender() {

        if (tabs.selectedTabPosition == 0) {
            var myCalander = Calendar.getInstance()

            var dateFromTag = ConvertDate.convertStringToDate(binding.mainDate.text.toString())!!

            myCalander = ConvertDate.dateToCalendar(dateFromTag)

            val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                myCalander.set(Calendar.YEAR, year)
                myCalander.set(Calendar.MONTH, month)
                myCalander.set(Calendar.DAY_OF_MONTH, day)
            }

            DatePickerDialog(
                this,
                datePicker, //values selected here will be saved under myCalander instance.......
                myCalander.get(Calendar.YEAR),
                myCalander.get(Calendar.MONTH),
                myCalander.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }


    fun pressDailyTab() {
        viewModel.filterDate(previousDate)
        binding.mainDate.text = previousDate
    }

    fun pressMonthlyTab() {
        if (tabs.selectedTabPosition != 1) {
            previousDate = binding.mainDate.text.toString()
            binding.mainDate.text = ConvertDate.getMonthName(binding.mainDate.text.toString())
            viewModel.filterMonth(ConvertDate.getShortMonthFromMonth(binding.mainDate.text.toString()))
        }
    }

    fun logOut() {
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }

    fun subtract() {
        var dateMinusOne = ""

        dateMinusOne = when (tabs.selectedTabPosition) {
            0 -> ConvertDate.subtractAccordingly(binding.mainDate.text.toString(), true)
            1 -> ConvertDate.subtractAccordingly(binding.mainDate.text.toString(), false)
            else -> ""
        }
        viewModel.filterAccordingly(dateMinusOne)
        viewModel.filteredDate = dateMinusOne
        binding.mainDate.text = dateMinusOne!!
    }

    fun add() {
        var datePlusOne = ""

        datePlusOne = when (tabs.selectedTabPosition) {
            0 -> ConvertDate.addAccordingly(binding.mainDate.text.toString(), true)
            1 -> ConvertDate.addAccordingly(binding.mainDate.text.toString(), false)
            else -> ""
        }
        viewModel.filterAccordingly(datePlusOne)
        viewModel.filteredDate = datePlusOne
        binding.mainDate.text = datePlusOne!!
    }

    fun showTransactions() {

        viewModel.allTransactions.observe(this) {
            it?.let {
                if (!it.isEmpty()){
                    Log.d("String List", it.toString())
                    transactionsRecycler.adapter = TransactionsAdapter(this, it)
                    binding.incomeValue.text = Calculate.calculateTotal(it).income.toString()
                    binding.expenseValue.text = Calculate.calculateTotal(it).expense.toString()
                    binding.totalValue.text = Calculate.calculateTotal(it).total.toString()
                }else{
                    transactionsRecycler.adapter = ShowEmptyAdapter(this)
                    binding.incomeValue.text = "0"
                    binding.expenseValue.text = "0"
                    binding.totalValue.text = "0"
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}