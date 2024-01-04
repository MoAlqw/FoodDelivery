package com.example.fooddelivery.model.food

interface FoodRepository {

    suspend fun getFoods(): List<Food>

    suspend fun getFoodById(id: Long): Food

    suspend fun insertFood(foodDBEntity: FoodDBEntity)

}