package com.example.fooddelivery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.model.exceptions.EmptyFieldException
import com.example.fooddelivery.model.location.LocationAdd
import com.example.fooddelivery.model.location.LocationRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class ChooseLocationViewModel(
    private val locationRepository: LocationRepository
): ViewModel() {

    private val _state = MutableLiveData<String>()
    val state: LiveData<String> = _state
    private val _inProgress = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean> = _inProgress
    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable){
            is EmptyFieldException -> emptyField(throwable)
        }
        hideProgress()
    }

    fun addLocation(newLocation: LocationAdd) {
        viewModelScope.launch(exceptionHandler) {
            showProgress()
            val validateLoc = launch {
                newLocation.validation()
            }
            val newLoc = launch {
                locationRepository.newLocation(newLocation)
            }

            validateLoc.join()
            newLoc.join()
            allGood()
        }
    }

    private fun allGood() {
        _isSuccessful.postValue(true)
    }


    private fun emptyField(e: EmptyFieldException) {
        _state.postValue("Empty ${e.field}")
    }

    private fun showProgress() {
        _inProgress.postValue(true)
    }

    private fun hideProgress() {
        _inProgress.postValue(false)
    }

}