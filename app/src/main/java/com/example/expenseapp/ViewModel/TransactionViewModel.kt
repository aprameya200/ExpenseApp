package com.example.expenseapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.Repository.TransactionRepository

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    val repository: TransactionRepository = TransactionRepository()

    private val _transactionAdded = MutableLiveData<Boolean>()
    val transactionAdded: LiveData<Boolean>
        get() = _transactionAdded

    private var _allTransactions = MutableLiveData<List<Transactions>>()

    init {
        var transactionsLD = getTransactions()

        transactionsLD.observeForever { transactionList ->
            // Update your LiveData with the new value
            _allTransactions.postValue(transactionList)
        }
    }
    val allTransactions: LiveData<List<Transactions>>
        get() = _allTransactions


    fun addTransaction(transaction: Transactions){
        _transactionAdded.value = repository.addTransaction(transaction)
    }

    fun getTransactions(): LiveData<List<Transactions>>{
//        _allTransactions = repository.getAllTransactions()

        return repository.getAllTransactions()
    }
}