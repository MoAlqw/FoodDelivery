package com.example.fooddelivery.model.location

import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun getAllLocations(): Flow<List<LocationDBEntity>>

    suspend fun checkLocations(): Boolean

    suspend fun newLocation(newLocation: LocationAdd)

}