package com.example.expenseapp.Repository

import androidx.lifecycle.LiveData
import com.example.expenseapp.Dao.TransactionsDao
import com.example.expenseapp.Entity.Transactions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val transactionsDao : TransactionsDao) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun getTransactionsFromRoom(): List<Transactions>{

        return transactionsDao.getAllTransactionsFromDatabase()
    }
}