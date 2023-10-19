package com.example.expenseapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.expenseapp.Database.ExpenseDatabase
import com.example.expenseapp.Repository.RoomRepository
import com.example.expenseapp.Repository.TransactionRepository

class DatabaseViewModel(application: Application) : AndroidViewModel(application) {

    val repository: RoomRepository

    init { //runs when the Class is first initialized
        val dao = ExpenseDatabase.getDatabase(application).getRoomDao()
        repository = RoomRepository(dao)

        repository.getTransactionsFromRoom()
    }
}