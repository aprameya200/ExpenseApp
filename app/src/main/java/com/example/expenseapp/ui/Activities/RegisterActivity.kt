package com.example.expenseapp.ui.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.expenseapp.Entity.User
import com.example.expenseapp.R
import com.example.expenseapp.databinding.ActivityRegisterBinding
import com.example.expenseapp.helpers.Validation
import com.example.expenseapp.viewmodel.LoginRegistrationViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance() //instance of firebase
    val db = FirebaseFirestore.getInstance()

    // Using ViewModelProvider directly
    val viewModel = ViewModelProvider(this)[LoginRegistrationViewModel::class.java]

// Example usage:
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.orange)

        binding.emailFeild.setText("aprameyanwopane@gmail.com")
        binding.confirmPassword.setText("User@123")
        binding.passwordFeild.setText("User@123")
        binding.fullNameField.setText("First User")

        binding.signUpButton.setOnClickListener {
            submitForm()
        }

    }

    fun submitForm() {
        var userName = binding.fullNameField.text.toString()
        var email = binding.emailFeild.text.toString()
        var password = binding.passwordFeild.text.toString()
        var confirmPassword = binding.confirmPassword.text.toString()

        var validation: Validation = Validation(userName,email,password,confirmPassword)

        if (validation.validateFields(this)) {
            val registerUser: User = User(userName,email,password)
            registerUserWithEmailAndPassword(registerUser)
        }

    }

    fun registerUserWithEmailAndPassword(userRegister: User) {
        auth.createUserWithEmailAndPassword(userRegister.email, userRegister.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

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
                        }
                        .addOnFailureListener { e ->
                            // Handle the error
                        }

                    sendEmailVerification()

                } else {
                    // User registration failed
                    val exception = task.exception
                    // Handle the error or show a message to the user
                }
            }
    }

    fun sendEmailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {


                    var intent =
                        Intent(this@RegisterActivity, WaitingVerificationActivity::class.java)
//                    auth.currentUser?.let { intent.putExtra("User", user) }
                    startActivity(intent)
                    finish()


                } else {
                    // Email verification link could not be sent
                    val exception = task.exception
                    // Handle the error or show a message to the user
                }
            }
    }
}