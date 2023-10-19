package com.example.expenseapp.Repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.expenseapp.Dao.TransactionsDao
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.enums.Category
import com.example.expenseapp.enums.TransactionType
import com.example.expenseapp.helpers.CheckInternet
import com.example.expenseapp.helpers.CheckInternet.Companion.isInternetAvailable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.concurrent.thread

class TransactionRepository(private val transactionsDao: TransactionsDao) {


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var transactionAdded = true

    fun addTransaction(transaction: Transactions): Boolean {

        GlobalScope.launch {
            transactionsDao.addTransactions(transaction)
        }

        auth.currentUser?.let {
            db.collection("Users")
                .document(auth.currentUser!!.uid)               // Reference the user's document
                .collection("Transactions")    // Reference the transactions sub-collection
                .add(transaction)           // Add the transaction document
                .addOnSuccessListener { documentReference ->
                    // Transaction added successfully
                    val transactionId = documentReference.id
                    Log.w(TAG, "ADDED  Succesfully")

                    transactionAdded = true
                    return@addOnSuccessListener
                    Log.w(TAG, transactionAdded.toString())

                }
                .addOnFailureListener { e ->
                    // Handle errors if the transaction addition fails
                    Log.w(TAG, "Error adding transaction", e)
                    transactionAdded = false
                    return@addOnFailureListener
                }
        }

        return transactionAdded
    }

    fun getAllTransactions(): LiveData<List<Transactions>> {
        val data = MutableLiveData<List<Transactions>>()

        // Replace "users" with your users collection and "transactions" with your subcollection
        auth.currentUser?.let {
            db.collection("Users")
                .document(it.uid)
                .collection("Transactions")
                .addSnapshotListener { querySnapshot, error ->
                    if (error != null) {
                        // Handle the error
                    } else {
                        val transactionList = mutableListOf<Transactions>()
                        if (querySnapshot != null) {
                            for (document in querySnapshot) {
//                                val transaction = document.toObject(Transactions::class.java)
                                val title = document.data["title"].toString()
                                val amount = document.data["amount"].toString().toDouble()
                                val category =
                                    Category.setEnumFromString(document.data["category"].toString())
                                val type =
                                    TransactionType.setTypeFromString(document.data["type"].toString())
                                val account = document.data["account"].toString()
                                val note = document.data["note"].toString()

                                val transaction = Transactions(
                                    null,
                                    title, category, type, account, note,
                                    Date(), amount
                                )
                                transactionList.add(transaction)
                            }
                        }
                        data.value = transactionList
                    }
                }
        }

        return data
    }


    fun flowOfData(): Flow<List<Transactions>> = callbackFlow {

        var fromLocalDatabase : List<Transactions> = listOf()


        withContext(Dispatchers.IO) {
            fromLocalDatabase = transactionsDao.getAllTransactionsFromDatabase()
        }

        trySend(fromLocalDatabase)

        withContext(Dispatchers.IO) {
            delay(10000L)
        }

        val data = MutableLiveData<List<Transactions>>()

        // Replace "users" with your users collection and "transactions" with your subcollection
        auth.currentUser?.let {
            db.collection("Users")
                .document(it.uid)
                .collection("Transactions")
                .addSnapshotListener { querySnapshot, error ->
                    if (error != null) {
                        // Handle the error
                    } else {
                        val transactionList = mutableListOf<Transactions>()
                        if (querySnapshot != null) {
                            for (document in querySnapshot) {
//                                val transaction = document.toObject(Transactions::class.java)
                                val title = document.data["title"].toString()
                                val amount = document.data["amount"].toString().toDouble()
                                val category =
                                    Category.setEnumFromString(document.data["category"].toString())
                                val type =
                                    TransactionType.setTypeFromString(document.data["type"].toString())
                                val account = document.data["account"].toString()
                                val note = document.data["note"].toString()

                                val transaction = Transactions(
                                    null,
                                    title, category, type, account, note,
                                    Date(), amount
                                )
                                transactionList.add(transaction)
                            }
                        }
//                        CoroutineScope(Dispatchers.IO).launch{
                            trySend(transactionList)
//                        }
                    }
                }
        }

        awaitClose()

//        getAllTransactions().value?.let {
//
//            emit(it)
//        }
    }


}