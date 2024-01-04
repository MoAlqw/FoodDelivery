package com.example.fooddelivery.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fooddelivery.Repositories
import com.example.fooddelivery.databinding.ActivityStartBinding

class ActivityStart : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartLogin.setOnClickListener{
            startActivity(Intent(this, ActivityLogin::class.java))
            finish()
        }
    }
}