package com.example.expenseapp.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.expenseapp.Entity.Transactions
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM transactions")
    fun getAllTransactionsFromDatabase() : Flow<List<Transactions>>

    @Insert
    fun addTransactions(transactions: Transactions)
}