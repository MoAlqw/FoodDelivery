package com.example.fooddelivery.model.account

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {

    @Query("SELECT id, password FROM accounts_of_users WHERE email = :email")
    suspend fun findByEmail(email: String): LoginUser?

    @Insert
    suspend fun createAccount(accountDBEntity: AccountDBEntity)

    @Query("SELECT * FROM accounts_of_users WHERE id = :accountID")
    fun getById(accountID: Long): Flow<AccountDBEntity?>

}