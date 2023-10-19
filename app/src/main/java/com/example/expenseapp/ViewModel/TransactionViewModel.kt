package com.example.expenseapp.ViewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.work.ListenableWorker
import com.example.expenseapp.Database.ExpenseDatabase
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.Repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    var repository: TransactionRepository

    private val _transactionAdded = MutableLiveData<Boolean>()
    val transactionAdded: LiveData<Boolean>
        get() = _transactionAdded

    private var _allTransactions = MutableLiveData<List<Transactions>>()

    val _loadLoad: MutableLiveData<List<Transactions>> = MutableLiveData<List<Transactions>>()

    val loadLoad: LiveData<List<Transactions>>
        get() = _loadLoad

    val StringList = MutableLiveData<List<Transactions>>()

    lateinit var allTransactionsLiveData: LiveData<List<Transactions>>

    init {

        val dao = ExpenseDatabase.getDatabase(application).getRoomDao()
        repository = TransactionRepository(dao)


        viewModelScope.launch(Dispatchers.Main) {
            repository.flowOfData().collect { result ->
                _allTransactions.value = result
            }
////
//        var transactionsLD = getTransactions()
//
//        transactionsLD.observeForever { transactionList ->
//            // Update your LiveData with the new value
//            _allTransactions.postValue(transactionList)
//            Log.d("THIS",transactionList.toString())
//
//        }
        }
    }

    val allTransactions: LiveData<List<Transactions>>
        get() = _allTransactions


    fun addTransaction(transaction: Transactions) {
        _transactionAdded.value = repository.addTransaction(transaction)
    }

    fun getTransactions(): LiveData<List<Transactions>> {
//        _allTransactions = repository.getAllTransactions()

        return repository.getAllTransactions()

    }

//    fun getRoomTransactions(): Flow<LiveData<List<Transactions>>> {
//        return  repository.getTransactionsCombined()
//    }
}