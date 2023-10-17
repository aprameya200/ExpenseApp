package com.example.expenseapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.expenseapp.Entity.User
import com.example.expenseapp.Repository.LoginRegisterRepository

class LoginRegistrationViewModel(application: Application) : AndroidViewModel(application) {

    val repository: LoginRegisterRepository = LoginRegisterRepository()

    private val _userRegistered = MutableLiveData<Boolean>()
    val userRegistered: LiveData<Boolean>
        get() = _userRegistered

    fun createUser(user: User) {
        _userRegistered.value = repository.registerUserWithEmailAndPassword(user)
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

