package com.example.fooddelivery.model.food

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomFoodRepository(
    private val foodDao: FoodDao
): FoodRepository {

    override suspend fun getFoods(): List<Food> {
        return withContext(Dispatchers.IO){
            return@withContext foodDao.allFood().map {
                it.toFood()
            }
        }
    }

    override suspend fun getFoodById(id: Long): Food {
        return withContext(Dispatchers.IO){
            return@withContext foodDao.getFoodById(id).toFood()
        }
    }

    override suspend fun insertFood(foodDBEntity: FoodDBEntity) {
        foodDao.insertFood(foodDBEntity)
    }

}