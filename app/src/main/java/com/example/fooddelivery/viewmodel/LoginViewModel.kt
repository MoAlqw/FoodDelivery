package com.example.fooddelivery.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.model.account.AccountsRepository
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.model.Field
import com.example.fooddelivery.model.account.AccountSharedPreferences
import com.example.fooddelivery.model.exceptions.AuthException
import com.example.fooddelivery.model.exceptions.EmptyFieldException
import com.example.fooddelivery.model.exceptions.NoLocationException
import com.example.fooddelivery.model.location.LocationRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class LoginViewModel(
    private val accountsRepository: AccountsRepository,
    private val locationRepository: LocationRepository
): ViewModel() {

    private val _state = MutableLiveData<String>()
    val state: LiveData<String> = _state
    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress
    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful
    private val _id = MutableLiveData<Long>()
    val id: LiveData<Long> = _id

    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        when(throwable) {
            is EmptyFieldException -> emptyField(throwable)
            is AuthException -> authError()
        }
        hideProgress()
    }

    fun login(email: String, password: String, context: Context) {
        viewModelScope.launch(exceptionHandler) {
            showProgress()
            val idOfUser = async {
                accountsRepository.logIn(email, password)
            }
            val id = idOfUser.await()
            saveIdAccount(id, context)
            allGood(id)
        }
    }


    private suspend fun allGood(id: Long) {
        _id.postValue(id)
        try {
            if (locationRepository.checkLocations()) _isSuccessful.postValue(true)
        } catch (e: NoLocationException) {
            _isSuccessful.postValue(false)
        }

    }

    private suspend fun saveIdAccount(id: Long, context: Context) {
        AccountSharedPreferences(context).saveAccountForAuthSplashScreen(id)
    }

    private fun emptyField(e: EmptyFieldException) {
        when(e.field){
            Field.username -> _state.postValue("Empty username")
            Field.email -> _state.postValue("Empty email")
            Field.password -> _state.postValue("Empty password")
            else -> {throw IllegalStateException()}
        }
    }

    private fun hideProgress() {
        _inProgress.postValue(false)
    }

    private fun authError() {
        _state.postValue("Account not found")
    }

    private fun showProgress() {
        _inProgress.postValue(true)
    }
}