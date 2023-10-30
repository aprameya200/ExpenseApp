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
            }

        }
    }



    fun addTransaction(transaction: Transactions) {
        _transactionAdded.value = repository.addTransaction(transaction)
    }

    fun filterDate(dateMinusOne: String){
        var allTransaction = _transactionsStore.value!!

        Log.d("All", _transactionsStore.value!!.size.toString())

        var filteredDate = mutableListOf<Transactions>()

        for (transaction in allTransaction){
            if (transaction.date == ConvertDate.convertStringToDate(dateMinusOne)){
                filteredDate.add(transaction)
                var bolleann = transaction.date == ConvertDate.convertStringToDate(dateMinusOne)
                Log.d("Transaction Bool HERE",bolleann.toString())
            }

            Log.d("Transactions",transaction.date.toString())
            Log.d("Transaction Conversion",
                ConvertDate.convertStringToDate(dateMinusOne).toString()
            )

        }

//        Log.d("Transactions",filteredDate.toString())
        _allTransactions.value = filteredDate //new vLUES ARE ADDED TO THIS LIST SO OTHER WONT GET UPDATED

    }


}