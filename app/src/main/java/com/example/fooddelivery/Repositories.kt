package com.example.fooddelivery

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fooddelivery.model.AppDataBase
import com.example.fooddelivery.model.Loader
import com.example.fooddelivery.model.account.AccountsRepository
import com.example.fooddelivery.model.account.RoomAccountsRepository
import com.example.fooddelivery.model.food.Food
import com.example.fooddelivery.model.food.FoodDBEntity
import com.example.fooddelivery.model.food.FoodRepository
import com.example.fooddelivery.model.food.RoomFoodRepository
import com.example.fooddelivery.model.location.LocationRepository
import com.example.fooddelivery.model.location.RoomLocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

object Repositories {

    private lateinit var applicationContext: Context

    private val database: AppDataBase by lazy {
        Room.databaseBuilder(applicationContext, AppDataBase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .addCallback(object: RoomDatabase.Callback(){
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Executors.newSingleThreadExecutor().execute {
                        val dao = database.getFoodDao()
                        val imgListFood = Loader().loadImgFoodMenu()
                        val nameListFood = Loader().loadNamesFood()
                        val priceListFood = Loader().loadPricesFood()
                        CoroutineScope(Job()).launch {
                            val insertData = launch {
                                for (i in 0.. 2) {
                                    dao.insertFood(FoodDBEntity.newFood(Food(0, imgListFood[i], nameListFood[i], priceListFood[i])))
                                }
                            }
                            insertData.join()
                        }

                    }
                }
            })
            .build()

    }

    val accountRepository: AccountsRepository by lazy {
        RoomAccountsRepository(database.getAccountsDao())
    }

    val locationRepository: LocationRepository by lazy {
        RoomLocationRepository(database.getLocationDao())
    }

    val foodRepository: FoodRepository by lazy {
        RoomFoodRepository(database.getFoodDao())
    }

    fun init(context: Context) {
        applicationContext = context
    }
}