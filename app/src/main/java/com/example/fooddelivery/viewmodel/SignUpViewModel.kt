package com.example.fooddelivery.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.model.Field
import com.example.fooddelivery.model.account.AccountSharedPreferences
import com.example.fooddelivery.model.account.AccountSignUp
import com.example.fooddelivery.model.account.AccountsRepository
import com.example.fooddelivery.model.exceptions.AccountAlreadyExistsException
import com.example.fooddelivery.model.exceptions.EmptyFieldException
import com.example.fooddelivery.model.exceptions.NoLocationException
import com.example.fooddelivery.model.location.LocationRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class SignUpViewModel(
    private val accountsRepository: AccountsRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _state = MutableLiveData<String>()
    val state: LiveData<String> = _state
    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress
    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful
    private val _id = MutableLiveData<Long>()
    val id: LiveData<Long> = _id

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when(throwable) {
            is EmptyFieldException -> emptyField(throwable)
            is AccountAlreadyExistsException -> accountAlreadyExists()
        }
        hideProgress()
    }

    fun signUp(signUpUser: AccountSignUp, context: Context) {
        viewModelScope.launch(exceptionHandler) {
            showProgress()
            val validateFields = launch {
                signUpUser.validate()
            }
            val createAccountFun = launch {
                accountsRepository.signUp(signUpUser)
            }

            validateFields.join()
            createAccountFun.join()
            trySave(signUpUser, context)
            allGood()
        }
    }

    private suspend fun allGood() {
        try {
            if (locationRepository.checkLocations()) _isSuccessful.postValue(true)
        } catch (e: NoLocationException) {
            _isSuccessful.postValue(false)
        }
    }

    private suspend fun trySave(signUpUser: AccountSignUp, context: Context) {
        val idFindAcc: Long = accountsRepository.logIn(signUpUser.email, signUpUser.password)
        _id.postValue(idFindAcc)
        if (idFindAcc != AccountSharedPreferences.NOT_FOUND) {
            AccountSharedPreferences(context).saveAccountForAuthSplashScreen(idFindAcc)
        }
    }

    private fun showProgress() {
        _inProgress.postValue(true)
    }

    private fun emptyField(e: EmptyFieldException) {
        when(e.field){
            Field.username -> _state.postValue("Empty username")
            Field.email -> _state.postValue("Empty email")
            Field.password -> _state.postValue("Empty password")
            else -> { throw IllegalStateException() }
        }
    }

    private fun accountAlreadyExists() {
        _state.postValue("Email already registered")
    }

    private fun hideProgress() {
        _inProgress.postValue(false)
    }

}