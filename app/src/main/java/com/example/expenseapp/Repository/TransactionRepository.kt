package com.example.expenseapp.Repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.expenseapp.Dao.TransactionsDao
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.enums.Category
import com.example.expenseapp.enums.TransactionType
import com.example.expenseapp.helpers.ConvertDate
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.concurrent.thread


//save and update data from user accordingly and delete data if user has logged out
// for first login , load data from firebase and save it in room
class TransactionRepository(private val transactionsDao: TransactionsDao) {


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

//    val transactionsCollection = db.collection("Users")
//        .document(auth.currentUser!!.uid)               // Reference the user's document
//        .collection("Transactions") //Collection inside the Users collection

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

    fun getAllTransactions(): Flow<List<Transactions>> = callbackFlow {

        var fromLocalDatabase: List<Transactions> = listOf()
//
//        withContext(Dispatchers.IO) {
//            fromLocalDatabase = transactionsDao.getAllTransactionsFromDatabase()
//        }
//
//        trySend(fromLocalDatabase)

        var transactionList = mutableListOf<Transactions>()

        // Replace "users" with your users collection and "transactions" with your subcollection
        auth.currentUser?.let {
            db.collection("Users")
                .document(auth.currentUser!!.uid)               // Reference the user's document
                .collection("Transactions")
                .addSnapshotListener { querySnapshot, error ->
                    if (error != null) {
                        // Handle the error
                    } else {
                        transactionList.clear() // Clear the list before adding new data
                        if (querySnapshot != null) {
                            for (document in querySnapshot) {
                                transactionList.add(parseTransactions(document))
                            }
                        }
                        trySend(transactionList)
//                        Log.d("Transactions Size", transactionList.size.toString())
//                        Log.d("Transactions Size Distict", transactionList.distinctBy {
//                            it.note
//                        }.size.toString())

                    }
                }
        }
        awaitClose()
    }

    fun parseTransactions(document: QueryDocumentSnapshot): Transactions {
        val title = document.data["title"].toString()
        val amount = document.data["amount"].toString().toDouble()
        val category = Category.setEnumFromString(document.data["category"].toString())
        val type = TransactionType.setTypeFromString(document.data["type"].toString())
        val account = document.data["account"].toString()
        val note = document.data["note"].toString()
        val date = ConvertDate.convertFirebaseTimestampToDate(document.data["date"] as Timestamp)

        Log.d("Date Format", date.toString())

        return Transactions(
            null,
            title, category, type, account, note,
            date, amount
        )
    }


}