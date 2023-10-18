package com.example.expenseapp.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.R
import com.example.expenseapp.ViewModel.TransactionViewModel
import com.example.expenseapp.databinding.ActivityMainBinding
import com.example.expenseapp.enums.Category
import com.example.expenseapp.enums.TransactionType
import com.example.expenseapp.ui.Adapters.TransactionsAdapter
import com.example.expenseapp.ui.Fragments.AddTransactionFragment
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var transactionsRecycler: RecyclerView

    lateinit var showDate: Calendar

    lateinit var viewModel: TransactionViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        viewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        supportActionBar?.title = "Transactions"
        window.statusBarColor = getColor(R.color.orange)


        var addTransactionSheet = AddTransactionFragment()


        binding.mainDate.text = formatDate(LocalDate.now())

        binding.backButton.setOnClickListener {
            binding.mainDate.text = subtractDate(binding.mainDate.text.toString())

            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent);
        }

        binding.forwardButton.setOnClickListener {
            binding.mainDate.text = addDate(binding.mainDate.text.toString())
        }


        binding.addTransaction.setOnClickListener {
            addTransactionSheet.show(supportFragmentManager, "Hi")
        }

        transactionsRecycler = binding.recyclerView
        transactionsRecycler.layoutManager = GridLayoutManager(this, 1)


        viewModel.allTransactions.observe(this){
            transactionsRecycler.adapter = TransactionsAdapter(this,it)
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