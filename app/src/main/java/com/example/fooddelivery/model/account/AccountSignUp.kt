package com.example.fooddelivery.model.account

import com.example.fooddelivery.model.Field
import com.example.fooddelivery.model.exceptions.EmptyFieldException

data class AccountSignUp(
    val username: String,
    val email: String,
    val password: String
){

    fun validate(){
        if (username.isBlank()) throw EmptyFieldException(Field.username)
        if (email.isBlank()) throw EmptyFieldException(Field.email)
        if (password.isBlank()) throw EmptyFieldException(Field.password)
    }

}