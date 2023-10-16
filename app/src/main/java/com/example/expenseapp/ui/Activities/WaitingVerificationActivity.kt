package com.example.expenseapp.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expenseapp.R
import com.example.expenseapp.databinding.ActivityWaitingVerificationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class WaitingVerificationActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance() //instance of firebase
    val currentUser = auth.currentUser
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    lateinit var binding :ActivityWaitingVerificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWaitingVerificationBinding.inflate(layoutInflater)

        setContentView(binding.root)

binding.backToLogIn.setOnClickListener {
    val intent = Intent(this,LoginActivity::class.java)
    startActivity(intent)
}


    }

//
}

