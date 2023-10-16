package com.example.expenseapp.helpers

import android.content.Context
import android.widget.Toast

class Validation(
    val fullName: String = "",
    val email: String,
    val password: String,
    val confirmPassword: String = ""
) {

    fun validateFields(context: Context): Boolean {
        var allValid: Boolean = true

        if (!isFullNameValid(fullName)) {
            Toast.makeText(context, "Full name is invalid", Toast.LENGTH_LONG).show()
            !allValid
        }
        if (!isEmailValid(email)) {
            Toast.makeText(context, "Email is invalid", Toast.LENGTH_LONG).show()
            !allValid
        }
        if (!isPasswordValid(password)) {
            Toast.makeText(context, "Password is invalid", Toast.LENGTH_LONG).show()
            !allValid
        }
        if (!confirmPassword(password, confirmPassword)) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_LONG).show()
            !allValid
        }

        if (allValid) {
            allValid = true
        }

        return allValid
    }

    fun isFullNameValid(fullName: String): Boolean {
        val regex = "^[a-zA-Z'\\- ]+\$"
        return fullName.matches(Regex(regex))
    }


    fun isEmailValid(email: String): Boolean {
        // Define a regex pattern for a valid email address
        val pattern = Regex("^[A-Za-z0-9+_.-]+@(.+)$")

        // Use the matches() function to check if the email matches the pattern
        return pattern.matches(email)
    }

    fun isPasswordValid(password: String): Boolean {
        // Define password validation criteria using regular expressions
        val minLength = 8
        val containsUppercase = Regex("[A-Z]")
        val containsLowercase = Regex("[a-z]")
        val containsDigit = Regex("[0-9]")
        val containsSpecialChar = Regex("[!@#\$%^&*()_+\\-=\\[\\]{};':\",.<>?~\\\\/]+")

        // Check if the password meets all the criteria
        return (password.length >= minLength &&
                containsUppercase.containsMatchIn(password) &&
                containsLowercase.containsMatchIn(password) &&
                containsDigit.containsMatchIn(password) &&
                containsSpecialChar.containsMatchIn(password))
    }

    fun confirmPassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }


}