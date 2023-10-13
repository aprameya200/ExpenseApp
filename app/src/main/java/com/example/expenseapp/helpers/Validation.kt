package com.example.expenseapp.helpers

class Validation {

    object validate {


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

    }
}