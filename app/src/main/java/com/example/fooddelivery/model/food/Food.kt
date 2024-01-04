package com.example.fooddelivery.model.food

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Food(
    val id: Long,
    val img: Int,
    val name: String,
    val price: Int
) : Parcelable {
}