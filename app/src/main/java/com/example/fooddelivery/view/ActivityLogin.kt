package com.example.fooddelivery.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.fooddelivery.Repositories
import com.example.fooddelivery.databinding.ActivityLoginBinding
import com.example.fooddelivery.viewmodel.LoginViewModel

class ActivityLogin : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModelLogin = LoginViewModel(
        Repositories.accountRepository,
        Repositories.locationRepository
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            tryLogin(this)
        }

        binding.btnCreateAcc.setOnClickListener {
            startActivity(Intent(this, ActivitySignUp::class.java))
            finish()
        }

        observeState()
        showToastMessage()
        observeLogin()

    }

    private fun tryLogin(context: Context) {
        val email = binding.editTextLogin.text.toString()
        val password = binding.editTextPassword.text.toString()
        viewModelLogin.login(email, password, context)
    }

    private fun observeLogin() {
        viewModelLogin.isSuccessful.observe(this) { isSuccessful ->
            if (isSuccessful.equals(true)) {
                startActivity(Intent(this, ActivityMainMenu::class.java)
                    .putExtra("id", viewModelLogin.id.value))
                finish()
            } else {
                startActivity(Intent(this, ActivityChooseLocation::class.java)
                    .putExtra("id", viewModelLogin.id.value))
                finish()
            }
        }
    }

    private fun observeState() = viewModelLogin.inProgress.observe(this) { inProgress ->
        with(binding) {
            loginBtn.isEnabled = !inProgress
            loginBtn.visibility = if (!inProgress) View.VISIBLE else View.INVISIBLE
            progressCreateAccount.visibility = if (inProgress) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun showToastMessage() {
        viewModelLogin.state.observe(this) { state ->
            Toast.makeText(this, state.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}