package com.example.expenseapp.ui.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.R
import com.example.expenseapp.ViewModel.TransactionViewModel
import com.example.expenseapp.databinding.ActivityMainBinding
import com.example.expenseapp.ui.Adapters.TransactionsAdapter
import com.example.expenseapp.ui.Fragments.AddTransactionFragment
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var transactionsRecycler: RecyclerView

    lateinit var showDate: Calendar

    val viewModel: TransactionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

//        val database = ExpenseDatabase.getDatabase(this)
//        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
//        roomModel = ViewModelProvider(this).get(DatabaseViewModel::class.java)

//        viewModel.getRoomTransactions()


        supportActionBar?.title = "Transactions"
        window.statusBarColor = getColor(R.color.orange)


        var addTransactionSheet = AddTransactionFragment()


        binding.mainDate.text = formatDate(LocalDate.now())

        binding.backButton.setOnClickListener {
            binding.mainDate.text = subtractDate(binding.mainDate.text.toString())

        }

        binding.forwardButton.setOnClickListener {
            binding.mainDate.text = addDate(binding.mainDate.text.toString())
        }


        binding.addTransaction.setOnClickListener {
            addTransactionSheet.show(supportFragmentManager, "Hi")
        }

        transactionsRecycler = binding.recyclerView
        transactionsRecycler.layoutManager = GridLayoutManager(this, 1)

//
//        val listOfAccounts = listOf<Transactions>(
//            Transactions( "Hello", Category.CASH, TransactionType.INCOME,"Savings","Nope",Date(),129.3),
//            Transactions( "Hello", Category.BUSINESS, TransactionType.EXPENSE,"Savings","Nope",Date(),129.3),
//            Transactions( "Hello", Category.CASH, TransactionType.EXPENSE,"Savings","Nope",Date(),129.3),
//            Transactions( "Hello", Category.LOAN, TransactionType.EXPENSE,"Savings","Nope",Date(),129.3),
//            Transactions( "Hello", Category.CASH, TransactionType.INCOME,"Savings","Nope",Date(),129.3),
//            Transactions( "Hello", Category.INVESTMENT, TransactionType.INCOME,"Savings","Nope",Date(),129.3),
//            Transactions( "Hello", Category.OTHERS, TransactionType.INCOME,"Savings","Nope",Date(),129.3),
//            Transactions( "Hello", Category.CASH, TransactionType.INCOME,"Savings","Nope",Date(),129.3),
//            Transactions( "Hello", Category.CASH, TransactionType.EXPENSE,"Savings","Nope",Date(),129.3),
//            )


//        viewModel.getTransactions()

        var items = listOf<Transactions>()

//        lifecycleScope.launch {
//            viewModel.getRoomTransactions().collect { transactions ->
//                items = transactions.value!!
//                transactionsRecycler.adapter = TransactionsAdapter(applicationContext, items)
//            }
//        }
//        transactionsRecycler.adapter = TransactionsAdapter(this, items)

//        viewModel.allTransactions.observe(this) {
//            it?.let {
//                transactionsRecycler.adapter = TransactionsAdapter(this, it)
//            }
//        }

        viewModel.allTransactions.observe(this) {
            it?.let {
                Log.d("String List",it.toString())
                transactionsRecycler.adapter = TransactionsAdapter(this, it)

            }
        }

    }

    fun subtractDate(date: String): String? {
        try {
            val dateAsDate =
                LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.US))
            val oneDayBefore = dateAsDate.minusDays(1)
            return oneDayBefore.format(DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.US))
        } catch (e: Exception) {
            return null // Handle parsing errors if necessary
        }
    }

    fun addDate(date: String): String? {
        try {
            val dateAsDate =
                LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.US))
            val oneDayBefore = dateAsDate.plusDays(1)
            return oneDayBefore.format(DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.US))
        } catch (e: Exception) {
            return null // Handle parsing errors if necessary
        }
    }


    fun formatDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.getDefault())
        return date.format(formatter)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}