package com.example.expenseapp.ui.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.expenseapp.Entity.User
import com.example.expenseapp.R
import com.example.expenseapp.databinding.ActivityLoginBinding
import com.example.expenseapp.helpers.Validation
import com.example.expenseapp.viewmodel.LoginRegistrationViewModel
import com.google.firebase.auth.FirebaseAuth

/// F O R G O T  P A S S W O R D

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    private var auth: FirebaseAuth = FirebaseAuth.getInstance() //instance of firebase
    lateinit var viewModel: LoginRegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (auth.currentUser != null){
            navigateToActivity(this,MainActivity::class.java)
        }else{
            initUI()
        }

    }

    private fun initUI() {
        window.statusBarColor = getColor(R.color.orange)

        viewModel = ViewModelProvider(this).get(LoginRegistrationViewModel::class.java)

        binding.loginButton.setOnClickListener {
            submitForm()
        }

        binding.signUp.setOnClickListener {
            navigateToActivity(this,RegisterActivity::class.java)
        }
    }

    fun submitForm() {
        var email = binding.emailFeild.text.toString()
        var password = binding.passwordFeild.text.toString()

        var validation = Validation("", email, password, "")
        val user = User("", email, password)

        //validation must also be done in view model
        if (validation.isEmailValid(email) && validation.isPasswordValid(password)) {
            viewModel.loginInUser(user)

            viewModel.userLoggedIn.observe(this) {
                if (it) {
                    navigateToActivity(this,MainActivity::class.java)
                } else {
                    Toast.makeText(this, "Please verify your email", Toast.LENGTH_SHORT).show()
                }
            }

        } else {
            Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_LONG).show()
        }
    }

    fun navigateToActivity(sourceActivity: Activity, targetActivityClass: Class<out Activity>) {
        val intent = Intent(sourceActivity, targetActivityClass)
        sourceActivity.startActivity(intent)
        finish()
    }

}