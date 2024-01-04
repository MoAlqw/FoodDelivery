package com.example.fooddelivery.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fooddelivery.Repositories
import com.example.fooddelivery.databinding.ActivityMainBinding
import com.example.fooddelivery.model.account.AccountSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getLongExtra(AccountSharedPreferences.ID_OF_USER, -1)

        binding.button2.setOnClickListener {
            startActivity(Intent(this, ActivityStart::class.java))
            finish()
        }

        binding.button.setOnClickListener {
            CoroutineScope(Job()).launch {
                getInfo(id)
            }
        }

    }

    private suspend fun getInfo(idUser: Long) {
        if (idUser == AccountSharedPreferences.NOT_FOUND) {
            Toast.makeText(this, "Account not founded", Toast.LENGTH_SHORT).show()
        } else {
            val accR = Repositories.accountRepository
            val getAcc = accR.getAccountWithId(idUser).collect {
                binding.usernameTv.text = it!!.username
                binding.emailTv.text = it.email
                binding.passwordTv.text = it.password
                binding.idTv.text = it.id.toString()
            }
        }
    }

}
