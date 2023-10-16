package com.example.expenseapp.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expenseapp.R
import com.example.expenseapp.databinding.ActivityRegisterBinding
import com.example.expenseapp.helpers.Validation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance() //instance of firebase


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
//
//            if (auth.currentUser?.isEmailVerified == true){
//                binding.signUp.setText("EEEEEE")
//            }
        }

    }

    fun submitForm() {
        var userName = binding.fullNameField.text
        var email = binding.emailFeild.text.toString()
        var password = binding.passwordFeild.text.toString()
        var confirmPasswprd = binding.confirmPassword.text.toString()

        if (Validation.isEmailValid(email) && Validation.isPasswordValid(password) && Validation.confirmPassword(
                password,
                confirmPasswprd
            )
        ) {
            registerUserWithEmailAndPassword(email, password)
        }
    }

    fun registerUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User registration successful
                    val user: FirebaseUser? = auth.currentUser
                    // You can now access the user's information using 'user'
                    // For example, user?.uid will give you the user's UID

                    // You may also want to send a verification email to the user
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


                        var intent = Intent(this@RegisterActivity, WaitingVerificationActivity::class.java)
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