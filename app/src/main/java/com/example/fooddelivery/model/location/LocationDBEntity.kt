package com.example.fooddelivery.model.location

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "location_of_user"
)
data class LocationDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val city: String,
    val street: String,
    @ColumnInfo(name = "is_private") val isPrivate: Boolean,
    val apartment: String?
) {

    fun toLocation(): Location = Location(
        id = id,
        city = city,
        street = street,
        isPrivate = isPrivate,
        apartment = apartment
    )

    companion object {
        fun newLocation(newLocation: LocationAdd): LocationDBEntity = LocationDBEntity(
            id = 0,
            city = newLocation.city,
            street = newLocation.street,
            isPrivate = newLocation.isPrivate,
            apartment = newLocation.apartment
        )
    }

}