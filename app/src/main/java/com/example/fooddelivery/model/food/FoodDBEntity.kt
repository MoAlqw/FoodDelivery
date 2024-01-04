package com.example.fooddelivery.model.food

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fooddelivery.model.account.AccountDBEntity
import com.example.fooddelivery.model.account.AccountSignUp

@Entity(
    tableName = "food"
)
data class FoodDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val img: Int,
    val name: String,
    val price: Int
) {

    fun toFood(): Food = Food(
        id = id,
        img = img,
        name = name,
        price = price
    )

    companion object {

        fun newFood(food: Food): FoodDBEntity = FoodDBEntity(
            id = 0,
            img = food.img,
            name = food.name,
            price = food.price
        )

    }

}