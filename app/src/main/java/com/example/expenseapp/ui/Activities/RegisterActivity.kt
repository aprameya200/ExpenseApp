package com.example.expenseapp.ui.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.expenseapp.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        window.statusBarColor = getColor(R.color.orange)

    }
}