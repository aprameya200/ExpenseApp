package com.example.expenseapp.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.expenseapp.R
import com.example.expenseapp.databinding.ActivityLoginBinding
import com.example.expenseapp.helpers.Validation
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    private var auth: FirebaseAuth = FirebaseAuth.getInstance() //instance of firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window.statusBarColor = getColor(R.color.orange)

        binding.loginButton.setOnClickListener{
            submitForm()
        }

        binding.signUp.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    fun submitForm() {
        var email = binding.emailFeild.text.toString()
        var password = binding.passwordFeild.text.toString()

        if (Validation.validate.isEmailValid(email) && Validation.validate.isPasswordValid(password)) {
            loginWithPasswordAndEmail(email, password)
        } else {
            Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_LONG).show()
        }
    }


    fun loginWithPasswordAndEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val userEmail = user?.email
                    var intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("Email",userEmail.toString())
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}