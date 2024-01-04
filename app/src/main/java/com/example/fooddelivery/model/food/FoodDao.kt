package com.example.fooddelivery.model.food

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodDao {

    @Query("SELECT * FROM food")
    suspend fun allFood(): List<FoodDBEntity>

    @Insert
    suspend fun insertFood(foodDBEntity: FoodDBEntity)

    @Query("SELECT * FROM food WHERE :id = id")
    suspend fun getFoodById(id: Long): FoodDBEntity

}