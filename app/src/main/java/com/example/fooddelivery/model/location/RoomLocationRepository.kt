package com.example.fooddelivery.model.location

import com.example.fooddelivery.model.exceptions.NoLocationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class RoomLocationRepository(
    private val locationDao: LocationDao
): LocationRepository {

    override suspend fun checkLocations(): Boolean {
        return withContext(Dispatchers.IO) {
            val locations = locationDao.selectAllLocation()
                .map {
                    if (it.isEmpty()) throw NoLocationException()
                }
            return@withContext true
        }
    }

    override suspend fun newLocation(newLocation: LocationAdd) {
        withContext(Dispatchers.IO) {
            addLocation(newLocation)
        }
    }

    override suspend fun getAllLocations(): Flow<List<LocationDBEntity>> {
        return withContext(Dispatchers.IO) {
            selectAllLocations()
        }
    }

    private suspend fun addLocation(newLocation: LocationAdd) {
        val locationEntity = LocationDBEntity.newLocation(newLocation)
        locationDao.addLocation(locationEntity)
    }

    private suspend fun getLocationById(id: Long): Flow<Location?> {
        return locationDao.getLocationById(id)
            .map { locationDBEntity ->  locationDBEntity?.toLocation() }
    }

    private fun selectAllLocations(): Flow<List<LocationDBEntity>> {
        val locations = locationDao.selectAllLocation()
        return locations
    }

}