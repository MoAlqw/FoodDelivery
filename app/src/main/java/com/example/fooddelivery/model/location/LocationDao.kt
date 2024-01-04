package com.example.fooddelivery.model.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert
    suspend fun addLocation(locationDBEntity: LocationDBEntity)

    @Query("SELECT * FROM location_of_user WHERE id = :id")
    fun getLocationById(id: Long): Flow<LocationDBEntity?>

    @Query("DELETE FROM location_of_user WHERE id = :id")
    suspend fun deleteLocationById(id: Long)

    @Query("SELECT * FROM location_of_user")
    fun selectAllLocation(): Flow<List<LocationDBEntity>>

}