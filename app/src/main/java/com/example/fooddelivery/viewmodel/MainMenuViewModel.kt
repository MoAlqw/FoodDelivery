package com.example.fooddelivery.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.R
import com.example.fooddelivery.model.food.FoodRepository
import com.example.fooddelivery.view.FragmentHome
import kotlinx.coroutines.launch

class MainMenuViewModel(
    private val foodRepository: FoodRepository
): ViewModel() {

    fun startFragment() {
        viewModelScope.launch {
        }
    }

}