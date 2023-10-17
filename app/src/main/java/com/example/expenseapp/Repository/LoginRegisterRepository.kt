package com.example.expenseapp.Repository

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.expenseapp.Entity.User
import com.example.expenseapp.ui.Activities.MainActivity
import com.example.expenseapp.ui.Activities.WaitingVerificationActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class LoginRegisterRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun registerUserWithEmailAndPassword(userRegister: User): Boolean {

        var emailSent = false
        var userCreated = false

        auth.createUserWithEmailAndPassword(userRegister.email, userRegister.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userCreated = true

                    val user: FirebaseUser? = auth.currentUser

                    val collection = db.collection("Users")
                    val data = hashMapOf(
                        "fullName" to userRegister.fullName,
                        "email" to userRegister.email,
                        "password" to userRegister.password,
                        // Add other fields as needed
                    )

                    collection.add(data)
                        .addOnSuccessListener { documentReference ->
                            // The documentReference variable contains the unique Document ID for the new object
                            val newDocumentId = documentReference.id
                            userCreated = true
                        }
                        .addOnFailureListener { e ->
                            // Handle the error
                            userCreated = false
                        }


                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                emailSent = true

                            } else {
                                val exception = task.exception
                                emailSent = false
                            }
                        }
                } else {
                    // User registration failed
                    val exception = task.exception
                    // Handle the error or show a message to the user
                    userCreated = false
                }
            }

        return userCreated == emailSent
    }

    fun loginWithPasswordAndEmail(user: User): Boolean {

        var userLogin = false
        var emailVerified = false

        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    userLogin = true

                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val userEmail = user?.email

                    //validation messages
                    emailVerified = user?.isEmailVerified == true

                } else {
                    userLogin = false
                }
            }

        return userLogin == emailVerified
    }

}