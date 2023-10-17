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
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance() //instance of firebase
    val db = FirebaseFirestore.getInstance()

    lateinit var viewModel: LoginRegistrationViewModel

    // Example usage:
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        window.statusBarColor = getColor(R.color.orange)

        with(binding) {
            emailFeild.setText("aprameyanwopane@gmail.com")
            confirmPassword.setText("User@123")
            passwordFeild.setText("User@123")
            fullNameField.setText("First User")
        }

        binding.signUpButton.setOnClickListener {
            submitForm()
        }

        binding.LogIn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

    fun submitForm() {
        var userName = binding.fullNameField.text.toString()
        var email = binding.emailFeild.text.toString()
        var password = binding.passwordFeild.text.toString()
        var confirmPassword = binding.confirmPassword.text.toString()

        var validation = Validation(userName, email, password, confirmPassword)

        if (validation.validateFields(this)) {
            val registerUser = User(userName, email, password)
            viewModel.createUser(registerUser)

            viewModel.userRegistered.observe(this) {
                if (it) {
                    startActivity(Intent(this@RegisterActivity, WaitingVerificationActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error Occured", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}