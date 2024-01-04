package com.example.fooddelivery.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.fooddelivery.Repositories
import com.example.fooddelivery.databinding.ActivitySignUpBinding
import com.example.fooddelivery.model.account.AccountSharedPreferences
import com.example.fooddelivery.model.account.AccountSignUp
import com.example.fooddelivery.viewmodel.SignUpViewModel

class ActivitySignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private val viewModelSignUp = SignUpViewModel(
        Repositories.accountRepository,
        Repositories.locationRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        Repositories.init(applicationContext)
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHaveAcc.setOnClickListener {
            startActivity(Intent(this, ActivityLogin::class.java))
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            onCreateAccountButtonPressed(this)
        }

        observeState()
        showToastMessage()
        observeLogin()

    }

    private fun observeLogin() {
        viewModelSignUp.isSuccessful.observe(this) { isSuccessful ->
            if (isSuccessful.equals(true)) {
                startActivity(Intent(this, ActivityMainMenu::class.java)
                    .putExtra("id", viewModelSignUp.id.value))
                finish()
            } else {
                startActivity(Intent(this, ActivityChooseLocation::class.java)
                    .putExtra("id", viewModelSignUp.id.value))
                finish()
            }
        }
    }

    private fun onCreateAccountButtonPressed(context: Context) {
        val signUpData = AccountSignUp(
            username = binding.editTextUsername.text.toString(),
            email = binding.editTextLogin.text.toString(),
            password = binding.editTextPassword.text.toString(),
        )
        viewModelSignUp.signUp(signUpData, context)
    }

    private fun observeState() = viewModelSignUp.inProgress.observe(this) { inProgress ->
        with(binding) {
            btnSignUp.isEnabled = !inProgress
            btnSignUp.visibility = if (!inProgress) View.VISIBLE else View.INVISIBLE
            progressCreateAccount.visibility = if (inProgress) View.VISIBLE else View.INVISIBLE
        }
    }

    private fun showToastMessage() {
        viewModelSignUp.state.observe(this) { state ->
            Toast.makeText(this, state.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}
