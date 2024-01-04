package com.example.fooddelivery.model.exceptions

import com.example.fooddelivery.model.Field

open class AppException(cause: Throwable) : RuntimeException(cause) {
}

class AccountAlreadyExistsException(
    cause: Throwable
) : AppException(cause = cause)

class AuthException: RuntimeException()

class EmptyFieldException(
    val field: Field
): RuntimeException()

class NoLocationException: RuntimeException()