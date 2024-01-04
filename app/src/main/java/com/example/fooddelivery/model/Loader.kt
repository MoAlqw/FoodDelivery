package com.example.fooddelivery.model

import com.example.fooddelivery.R

class Loader {

    fun loadImgBanners(): List<Int> {
        return listOf(
            R.drawable.banner_1,
            R.drawable.banner_2,
            R.drawable.banner_3,
            R.drawable.banner_4,
            R.drawable.banner_5,
            R.drawable.banner_6,
            R.drawable.banner_7,
            R.drawable.banner_8,
            R.drawable.banner_9,
            R.drawable.banner_10,
            R.drawable.banner_11,
            R.drawable.banner_12,
            R.drawable.banner_13
        )
    }

    fun loadImgFoodMenu(): List<Int> {
        return listOf(
            R.drawable.pop_menu_burger,
            R.drawable.pop_menu_momo,
            R.drawable.pop_menu_sandwich
        )
    }

    fun loadNamesFood(): List<String> {
        return listOf(
            "Herbal Pancake",
            "Fruit Salad",
            "Green Noddle"
        )
    }

    fun loadPricesFood(): List<Int> {
        return listOf(7, 5, 15)
    }

}