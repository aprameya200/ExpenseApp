package com.example.expenseapp.Repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.enums.Category
import com.example.expenseapp.enums.TransactionType
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.Date

class TransactionRepository {


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var transactionAdded = true


    fun addTransaction(transaction: Transactions): Boolean {

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

    fun getTransactions(): List<Transactions> {

        val allTransactions: MutableList<Transactions> = mutableListOf()

        val usersCollection = db.collection("Users")

// Reference the user's document based on UID
        val userDocument = auth.currentUser?.let { usersCollection.document(it.uid) }

// Reference the "Transactions" collection within the user's document
        val transactionsCollection = userDocument?.collection("Transactions")

// Query for all transactions in the "Transactions" collection
        if (transactionsCollection != null) {
            transactionsCollection.get()
                .addOnSuccessListener { querySnapshot: QuerySnapshot ->
                    for (document in querySnapshot) {

                        val transactionData = document.data
                        // Process the transaction data as needed

                        val title = transactionData["title"]
                        val amout = transactionData["amount"]

                        val transaction = Transactions(title.toString(),Category.OTHERS,TransactionType.EXPENSE,"cash","aa",
                            Date(),amout.toString().toDouble()
                        )

                        allTransactions.add(transaction)
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle any errors that occur during the query
                }
        }

        return allTransactions
    }

}