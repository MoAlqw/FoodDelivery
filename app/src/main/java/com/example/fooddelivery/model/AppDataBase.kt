package com.example.fooddelivery.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fooddelivery.model.account.AccountDBEntity
import com.example.fooddelivery.model.account.AccountsDao
import com.example.fooddelivery.model.food.FoodDBEntity
import com.example.fooddelivery.model.food.FoodDao
import com.example.fooddelivery.model.location.LocationDBEntity
import com.example.fooddelivery.model.location.LocationDao

@Database(
    version = 4,
    entities = [AccountDBEntity::class, LocationDBEntity::class, FoodDBEntity::class],
)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getAccountsDao(): AccountsDao

    abstract fun getLocationDao(): LocationDao

    abstract fun getFoodDao(): FoodDao

}