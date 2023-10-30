package com.example.expenseapp.ui.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.R
import com.example.expenseapp.ViewModel.TransactionViewModel
import com.example.expenseapp.databinding.ActivityMainBinding
import com.example.expenseapp.helpers.Calculate
import com.example.expenseapp.helpers.ConvertDate
import com.example.expenseapp.ui.Adapters.TransactionsAdapter
import com.example.expenseapp.ui.Fragments.AddTransactionFragment
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var transactionsRecycler: RecyclerView

    lateinit var viewModel: TransactionViewModel

    private val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)


        supportActionBar?.title = "Transactions"
        window.statusBarColor = getColor(R.color.orange)

        viewModel = TransactionViewModel(application)



        var addTransactionSheet = AddTransactionFragment()

        binding.mainDate.text = ConvertDate.formatDate(LocalDate.now())

        binding.backButton.setOnClickListener {
            var dateMinusOne = ConvertDate.subtractDate(binding.mainDate.text.toString())
            binding.mainDate.text = dateMinusOne!!
            viewModel.filterDate(dateMinusOne)

            //do not call this 2 times, make it obeserve once

//            viewModel.filterTransactions.observe(this) {
//                it?.let {
//                    Log.d("String List", it.toString())
//                    transactionsRecycler.adapter = TransactionsAdapter(this, it)
//                    binding.incomeValue.text = Calculate.calculateTotal(it).income.toString()
//                    binding.expenseValue.text = Calculate.calculateTotal(it).expense.toString()
//                    binding.totalValue.text = Calculate.calculateTotal(it).total.toString()
//                }
//            }
        }

        binding.logOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.forwardButton.setOnClickListener {
            binding.mainDate.text = ConvertDate.addDate(binding.mainDate.text.toString())
        }

        binding.addTransaction.setOnClickListener {
            addTransactionSheet.show(supportFragmentManager, "Hi")
        }

        transactionsRecycler = binding.recyclerView
        transactionsRecycler.layoutManager = GridLayoutManager(this, 1)


        var items = listOf<Transactions>()

        showTransactions()

    }

    fun showTransactions(){
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