package com.example.expenseapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LoginRegistrationViewModel(application: Application) : AndroidViewModel(application) {

    fun createUser() {
        // Call the repository to create a user
//        userRepository.createUser { success ->
//            _createUserStatus.value = success
//        }
    }

    fun addUserToDB() {
        // Logic to add user data to a database
//        userRepository.addUserToDB()
    }

    fun signIn() {
        // Call the repository to perform user sign-in
//        userRepository.signIn { success ->
//            _signInStatus.value = success
//        }
    }

    fun verifyEmail() {
        // Logic to send email verification and verify user email
//        userRepository.verifyEmail()
    }
}

