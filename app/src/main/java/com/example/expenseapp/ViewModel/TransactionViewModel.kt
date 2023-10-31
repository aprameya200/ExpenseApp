package com.example.expenseapp.ViewModel

import android.app.Application
import android.util.Log

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.expenseapp.Database.ExpenseDatabase
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.Repository.TransactionRepository
import com.example.expenseapp.helpers.ConvertDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.DEFAULT_CONCURRENCY_PROPERTY_NAME

import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Date

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    var repository: TransactionRepository


    private val _transactionAdded = MutableLiveData<Boolean>()
    val transactionAdded: LiveData<Boolean>
        get() = _transactionAdded

    private var _allTransactions = MutableLiveData<List<Transactions>>()

    private var _filteredTransactions = MutableLiveData<List<Transactions>>()

    private var _transactionsStore = MutableLiveData<List<Transactions>>()


    val allTransactions: LiveData<List<Transactions>>
        get() = _allTransactions

    val filterTransactions: LiveData<List<Transactions>>
        get() = _filteredTransactions

    val transactionsStore: LiveData<List<Transactions>>
        get() = _transactionsStore

    init {

        val dao = ExpenseDatabase.getDatabase(application).getRoomDao()
        repository = TransactionRepository(dao)

        viewModelScope.launch(Dispatchers.Main) {
            repository.getAllTransactions().collect { result ->
                _allTransactions.value = result
                _transactionsStore.value = result
                filterDate(ConvertDate.formatDate(LocalDate.now()))
            }

        }
    }



    fun addTransaction(transaction: Transactions) {
        _transactionAdded.value = repository.addTransaction(transaction)
    }

    fun filterDate(date: String){

        var allTransaction = _transactionsStore.value!!
        var filteredDate = mutableListOf<Transactions>()

        for (transaction in allTransaction){
            if (transaction.date == ConvertDate.convertStringToDate(date)){
                filteredDate.add(transaction)
            }
        }
        _allTransactions.value = filteredDate //new vLUES ARE ADDED TO THIS LIST SO OTHER WONT GET UPDATED
    }

    fun filterMonth(month: String){

        var allTransaction = _transactionsStore.value!!
        var filteredDate = mutableListOf<Transactions>()

        for (transaction in allTransaction){
            Log.d("Month",transaction.date.toString())
            if (transaction.date.toString().contains(month)){
                filteredDate.add(transaction)

            }
        }
        _allTransactions.value = filteredDate //new vLUES ARE ADDED TO THIS LIST SO OTHER WONT GET UPDATED
    }


}