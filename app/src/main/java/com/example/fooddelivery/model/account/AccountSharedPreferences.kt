package com.example.fooddelivery.model.account

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountSharedPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        NAME_PREFERENCES,
        Context.MODE_PRIVATE
    )

    suspend fun saveAccountForAuthSplashScreen(id: Long) {
        withContext(Dispatchers.IO) {
            val editSharedPreferences: SharedPreferences.Editor = sharedPreferences.edit()
            editSharedPreferences.putLong(ID_OF_USER, id).apply()
        }
    }
    fun forLogInAccountInSplashScreen(): Long {
        if (sharedPreferences.contains(ID_OF_USER)) {
            return sharedPreferences.getLong(ID_OF_USER, -1)
        }
        return -1
    }
    companion object {
        const val NAME_PREFERENCES = "id_of_user"
        const val ID_OF_USER = "id_of_user"
        const val NOT_FOUND: Long = -1
    }

}