package com.example.fooddelivery.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddelivery.R
import com.example.fooddelivery.Repositories
import com.example.fooddelivery.model.account.AccountSharedPreferences
import com.example.fooddelivery.model.exceptions.NoLocationException
import com.example.fooddelivery.model.location.LocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val context = this

        CoroutineScope(Job()).launch {
            delay(1000)
            val tryLogin = launch {
                tryLogin(context, Repositories.locationRepository)
            }
            tryLogin.join()
        }

    }

    private suspend fun tryLogin(context: Context, locationRepository: LocationRepository) {
        val resId = AccountSharedPreferences(context).forLogInAccountInSplashScreen()
        if (resId == AccountSharedPreferences.NOT_FOUND) {
            startActivity(Intent(this, ActivityStart::class.java))
            finish()
        } else {
            try {
                if (locationRepository.checkLocations()) {
                    val intent = Intent(this, ActivityMainMenu::class.java)
                    startActivity(intent)
                    finish()
                }
            } catch (e: NoLocationException) {
                startActivity(Intent(this, ActivityChooseLocation::class.java))
                finish()
            }
        }
    }

}