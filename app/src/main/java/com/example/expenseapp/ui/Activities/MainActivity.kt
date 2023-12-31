package com.example.expenseapp.ui.Activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseapp.R
import com.example.expenseapp.ViewModel.TransactionViewModel
import com.example.expenseapp.databinding.ActivityMainBinding
import com.example.expenseapp.helpers.Calculate
import com.example.expenseapp.helpers.ConvertDate
import com.example.expenseapp.ui.Adapters.ShowEmptyAdapter
import com.example.expenseapp.ui.Adapters.TransactionListAdapter
import com.example.expenseapp.ui.Adapters.TransactionsAdapter
import com.example.expenseapp.ui.Fragments.Dialogs.AddTransactionFragment
import com.example.expenseapp.ui.Fragments.Dialogs.ChartsFragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar

/**
 * BUGS
 *
 *
 * List adapter wont load valid date
 * Year sakepachi new months ko dehkhaunu parchs, but not it shows of the previous year months also (not yesr specific)
 *
 *
 * REMAINING
 *
 */

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var transactionsRecycler: RecyclerView
    lateinit var viewModel: TransactionViewModel

    private val auth = FirebaseAuth.getInstance()

    var previousDate: String = ""
    lateinit var previousMonth: String

    lateinit var tabs: TabLayout

    var showCalander = false

    var listAdapter = TransactionListAdapter()


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
            summary.view
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
                    summary.view -> showSummary()
                }
            }
        }

        transactionsRecycler = binding.recyclerView
        transactionsRecycler.layoutManager = GridLayoutManager(this, 1)

        transactionsRecycler.adapter = listAdapter

        showTransactions()
        navigationListeners()

    }

    @SuppressLint("SuspiciousIndentation")
    fun showTransactions() {

        viewModel.allTransactions.observe(this) { transactions ->
            transactions?.let { transactionList ->
                val isEmpty = transactionList.isEmpty()
                if (!isEmpty){
                    transactionsRecycler.adapter = listAdapter
                    listAdapter.submitList(transactionList)
                }
                else {
                    transactionsRecycler.adapter = ShowEmptyAdapter(this)
                }
                val total = Calculate.calculateTotal(transactionList)
                binding.incomeValue.text = total.income.toString()
                binding.expenseValue.text = total.expense.toString()
                binding.totalValue.text = total.total.toString()
            }
        }
    }

    fun navigationListeners() {

        val charts = ChartsFragment()
        val fragment = binding.fragmentContainer
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.charts -> addOrRemoveFragment(fragment, charts, true)
                R.id.transaction -> addOrRemoveFragment(fragment, charts, false)
                else -> {
                    addOrRemoveFragment(fragment, charts, false)
                }
            }
        }
    }

    fun addOrRemoveFragment(view: View, fragment: Fragment, add: Boolean): Boolean {
        val transaction = supportFragmentManager.beginTransaction()
        if (add) {
            transaction.replace(view.id, fragment)
            transaction.addToBackStack(null)
            binding.incomeExpense.visibility = View.GONE
            binding.addTransaction.visibility = View.GONE
        } else {
            transaction.remove(fragment)
            binding.incomeExpense.visibility = View.VISIBLE
        }
        transaction.commit()

        return true
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

                binding.mainDate.text =
                    SimpleDateFormat("dd MMMM, yyyy").format(myCalander.time).toString()
                viewModel.filterDate(binding.mainDate.text.toString())
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

    fun toggleVisibility(turnOff: Boolean) {
        val mainDateVisibility = if (turnOff) View.GONE else View.VISIBLE
        val forwardButtonVisibility = mainDateVisibility
        val backButtonVisibility = mainDateVisibility
        val summaryTitleVisibility = if (turnOff) View.VISIBLE else View.GONE

        binding.mainDate.visibility = mainDateVisibility
        binding.forwardButton.visibility = forwardButtonVisibility
        binding.backButton.visibility = backButtonVisibility
        binding.summaryTitle.visibility = summaryTitleVisibility
    }

    fun showSummary() {

        if (previousDate.length > 10 && binding.mainDate.text.toString().length > 10) previousDate =
            ""
        toggleVisibility(true)
        viewModel.showAllTransactions()
    }

    fun pressDailyTab() {
        toggleVisibility(false)
        if (tabs.selectedTabPosition != 0) {
            if (previousDate.isEmpty()) previousDate = binding.mainDate.text.toString()

            //send old value to method
            viewModel.filterAccordingly(previousDate)
            binding.mainDate.text = previousDate
        }
    }

    fun pressMonthlyTab() {
        toggleVisibility(false)
        if (tabs.selectedTabPosition != 1) {

            if (binding.mainDate.text.toString().length > 10) previousDate =
                binding.mainDate.text.toString()

//            previousDate = binding.mainDate.text.toString()
            previousMonth = binding.mainDate.text.toString()
            if (binding.mainDate.text.toString().length > 10) {
                binding.mainDate.text = ConvertDate.getMonthName(binding.mainDate.text.toString())
                viewModel.filterMonth(ConvertDate.getShortMonthFromMonth(binding.mainDate.text.toString()))
            } else {
                viewModel.filterMonth(ConvertDate.getShortMonthFromMonth(binding.mainDate.text.toString()))
            }

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




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}