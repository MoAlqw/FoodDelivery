package com.example.fooddelivery.model.location

import com.example.fooddelivery.model.Field
import com.example.fooddelivery.model.exceptions.EmptyFieldException

data class LocationAdd(
    val city: String,
    val street: String,
    val isPrivate: Boolean,
    val apartment: String?
) {

    fun validation() {
        if (city.isBlank()) throw EmptyFieldException(Field.city)
        if (street.isBlank()) throw EmptyFieldException(Field.street)
        if (!isPrivate && apartment == "") throw EmptyFieldException(Field.apartment)
    }

}