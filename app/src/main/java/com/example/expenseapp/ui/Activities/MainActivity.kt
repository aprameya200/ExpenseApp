package com.example.expenseapp.ui.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.R
import androidx.lifecycle.ViewModel
import com.example.expenseapp.ViewModel.TransactionViewModel
import com.example.expenseapp.databinding.ActivityMainBinding
import com.example.expenseapp.helpers.Calculate
import com.example.expenseapp.helpers.ConvertDate
import com.example.expenseapp.ui.Adapters.TransactionsAdapter
import com.example.expenseapp.ui.Fragments.AddTransactionFragment
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var transactionsRecycler: RecyclerView

    lateinit var viewModel: TransactionViewModel

    var monthTab = false
    var dailyTab = false


    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)


        supportActionBar?.title = "Transactions"
        window.statusBarColor = getColor(R.color.orange)

        viewModel = TransactionViewModel(application)

        monthTab = binding.selectTabs.selectedTabPosition.toString() == "1"
        dailyTab = binding.selectTabs.selectedTabPosition.toString() == "0"



        var addTransactionSheet = AddTransactionFragment()

        binding.mainDate.text = ConvertDate.formatDate(LocalDate.now())

        binding.backButton.setOnClickListener {
            if (dailyTab){
                var dateMinusOne = ConvertDate.subtractDate(binding.mainDate.text.toString())
                binding.mainDate.text = dateMinusOne!!
                viewModel.filterDate(dateMinusOne)
            }
            if (monthTab){
                var dateMinusOne = ConvertDate.subtractMonth(binding.mainDate.text.toString())
                Log.d("New month",dateMinusOne.toString())
                binding.mainDate.text = dateMinusOne!!
                viewModel.filterMonth(ConvertDate.getShortMonthFromMonth(dateMinusOne))
            }

            //do not call this 2 times, make it obeserve once
        }

        binding.forwardButton.setOnClickListener {
            if (dailyTab){
                var datePlusOne = ConvertDate.addDate(binding.mainDate.text.toString())
                binding.mainDate.text = datePlusOne!!
                viewModel.filterDate(datePlusOne)
            }
            if (monthTab){
                var datePlusOne = ConvertDate.addMonth(binding.mainDate.text.toString())
                Log.d("New month",datePlusOne.toString())
                binding.mainDate.text = datePlusOne!!
                viewModel.filterMonth(ConvertDate.getShortMonthFromMonth(datePlusOne))
            }

        }

        binding.logOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.addTransaction.setOnClickListener {
            addTransactionSheet.show(supportFragmentManager, "Hi")
        }

        transactionsRecycler = binding.recyclerView
        transactionsRecycler.layoutManager = GridLayoutManager(this, 1)

        showTransactions()

        val daily = binding.selectTabs.getTabAt(0)!!
        val monthly = binding.selectTabs.getTabAt(1)!!
        val calander = binding.selectTabs.getTabAt(2)!!
        val summary = binding.selectTabs.getTabAt(3)!!

        monthly.view.setOnClickListener {
            if (!monthTab){
                binding.mainDate.text = ConvertDate.getMonthName(binding.mainDate.text.toString())
                viewModel.filterMonth(ConvertDate.getShortMonthFromMonth(binding.mainDate.text.toString()))
            }

        }

        daily.view.setOnClickListener {
            viewModel.filterDate(ConvertDate.formatDate(LocalDate.now()))
            binding.mainDate.text = ConvertDate.formatDate(LocalDate.now())

        }


    }

    fun showTransactions() {
        viewModel.allTransactions.observe(this) {
            it?.let {
                Log.d("String List", it.toString())
                transactionsRecycler.adapter = TransactionsAdapter(this, it)
                binding.incomeValue.text = Calculate.calculateTotal(it).income.toString()
                binding.expenseValue.text = Calculate.calculateTotal(it).expense.toString()
                binding.totalValue.text = Calculate.calculateTotal(it).total.toString()
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}