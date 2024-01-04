package com.example.fooddelivery.model.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity (
    tableName = "accounts_of_users",
    indices = [
        Index("email", unique = true)
    ]
)
data class AccountDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val email: String,
    val username: String,
    val password: String
    ) {

    fun toAccount(): Account = Account(
        id = id,
        email = email,
        username = username,
        password = password
    )

    companion object {

        fun newAccount(signUpUser: AccountSignUp): AccountDBEntity = AccountDBEntity(
            id = 0,
            email = signUpUser.email,
            username = signUpUser.username,
            password = signUpUser.password
        )

    }

}