package com.example.fooddelivery.model.account

import android.database.sqlite.SQLiteConstraintException
import com.example.fooddelivery.model.Field
import com.example.fooddelivery.model.exceptions.AccountAlreadyExistsException
import com.example.fooddelivery.model.exceptions.AuthException
import com.example.fooddelivery.model.exceptions.EmptyFieldException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomAccountsRepository(
    private val accountsDao: AccountsDao
): AccountsRepository {

    override suspend fun signUp(signUpAccount: AccountSignUp) {
        withContext(Dispatchers.IO) {
            createAccount(signUpAccount)
        }
    }

    override suspend fun logIn(email: String, password: String): Long {
        return withContext(Dispatchers.IO) {
            findAccountByEmailAndPassword(email, password)
        }
    }

    override suspend fun getAccountWithId(id: Long): Flow<Account?> {
        return withContext(Dispatchers.IO) {
            getAccountById(id)
        }
    }

    private suspend fun findAccountByEmailAndPassword(email: String, password: String): Long {
        if (email.isBlank()) throw EmptyFieldException(Field.email)
        if (password.isBlank()) throw EmptyFieldException(Field.password)
        val tuple = accountsDao.findByEmail(email) ?: throw AuthException()
        if (tuple.password != password) throw AuthException()
        return tuple.id
    }

    private suspend fun createAccount(signUp: AccountSignUp) {
        try {
            val entity = AccountDBEntity.newAccount(signUp)
            accountsDao.createAccount(entity)
        } catch (e: SQLiteConstraintException) {
            val appException = AccountAlreadyExistsException(e)
            throw appException
        }
    }


    private suspend fun getAccountById(accountID: Long): Flow<Account?> {
        return accountsDao.getById(accountID)
            .map { accountDBEntity -> accountDBEntity?.toAccount() }
    }

}