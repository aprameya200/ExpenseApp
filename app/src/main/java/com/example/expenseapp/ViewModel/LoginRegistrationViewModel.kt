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


    private val _userLoggedIn = MutableLiveData<Boolean>()
    val userLoggedIn: LiveData<Boolean>
        get() = _userLoggedIn

    fun createUser(user: User) {
        _userRegistered.value = repository.registerUserWithEmailAndPassword(user)
    }

    fun loginInUser(user: User){
        _userLoggedIn.value = repository.loginWithPasswordAndEmail(user)
    }

    fun logout(){
        repository.logout()
    }

    fun addUserToDB() {
        // Logic to add user data to a database
//        userRepository.addUserToDB()
    }


    fun verifyEmail() {
        // Logic to send email verification and verify user email
//        userRepository.verifyEmail()
    }
}

