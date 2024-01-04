package com.example.fooddelivery.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.fooddelivery.R
import com.example.fooddelivery.Repositories
import com.example.fooddelivery.databinding.ActivityMainMenuBinding
import com.example.fooddelivery.model.food.Food
import com.example.fooddelivery.viewmodel.MainMenuViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ActivityMainMenu : AppCompatActivity() {

    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var foods: List<Food>
    private val foodRepository = Repositories.foodRepository
    //private val viewModelMenu by lazy { MainMenuViewModel(foodRepository) }
    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val id = (intent.getLongExtra("id", -1))

        CoroutineScope(Job()).launch {
            val getFoods = async {
                foodRepository.getFoods()
            }
            foods = getFoods.await()

            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, FragmentHome.newInstance(foods))
                .commit()

        }
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}