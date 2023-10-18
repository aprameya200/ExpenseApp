package com.example.expenseapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expenseapp.Dao.TransactionsDao
import com.example.expenseapp.Entity.Transactions

@Database(entities = [Transactions::class], version = 1)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun getTransactionsDao() : TransactionsDao

    companion object{
        @Volatile
        var INSTANCE : ExpenseDatabase? = null

        fun getDatabase(context: Context): ExpenseDatabase{ //get the current context
            var tempInstance = INSTANCE

            tempInstance?.let{
                return tempInstance
            }
            synchronized(this){
                val dbBuilder = Room.databaseBuilder(context,ExpenseDatabase::class.java,"Expense").allowMainThreadQueries().build()
                INSTANCE = dbBuilder
                return dbBuilder
            }
        }
    }
}