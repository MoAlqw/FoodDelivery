package com.example.fooddelivery.model.location

data class Location(
    val id: Long,
    val city: String,
    val street: String,
    val isPrivate: Boolean,
    val apartment: String?
) {
}