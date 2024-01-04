package com.example.fooddelivery.model.account

import kotlinx.coroutines.flow.Flow

interface AccountsRepository {

    suspend fun signUp(signUpAccount: AccountSignUp)

    suspend fun logIn(email: String, password: String): Long

    suspend fun getAccountWithId(id: Long): Flow<Account?>

}