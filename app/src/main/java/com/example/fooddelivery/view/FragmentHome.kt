package com.example.fooddelivery.view

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.fooddelivery.MenuFoodAdapter
import com.example.fooddelivery.ViewPagerAdapter
import com.example.fooddelivery.databinding.FragmentHomeBinding
import com.example.fooddelivery.model.Loader
import com.example.fooddelivery.model.food.Food

class FragmentHome: Fragment() {

    private val imgListBanner = Loader().loadImgBanners()

    private lateinit var pager2: ViewPager2
    private lateinit var adapterViewPager: ViewPagerAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterRecycler: MenuFoodAdapter
    private lateinit var foods: Array<Food>
    private lateinit var imgListFood: MutableList<Int>
    private lateinit var nameListFood: MutableList<String>
    private lateinit var priceListFood: MutableList<Int>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        foods = arguments?.getParcelableArray(LIST_FOOD)!! as Array<Food>
        for (i in 0..foods.size) {
            imgListFood.add(foods[i].img)
            nameListFood.add(foods[i].name)
            priceListFood.add(foods[i].price)
        }
        init(binding)
        return binding.root
    }

    private fun init(binding: FragmentHomeBinding) {
//        pager2 = binding.viewPagerOfBanners
//        adapterViewPager = ViewPagerAdapter(imgListBanner, pager2)
//        pager2.adapter = adapterViewPager
//        pager2.offscreenPageLimit = 2
//        pager2.clipChildren = false

        recyclerView = binding.rvMenu
        adapterRecycler = MenuFoodAdapter(imgListFood, nameListFood, priceListFood)
        recyclerView.adapter = adapterRecycler
    }

    companion object {
        private const val LIST_FOOD = "key_of_list_food"
        fun newInstance(foods: List<Food>): FragmentHome {
            val args = Bundle()
            args.putParcelableArray(LIST_FOOD, foods.toTypedArray())
            val fragment = FragmentHome()
            fragment.arguments = args
            return fragment
        }
    }

}