package com.example.oniki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.oniki.api.LoginResponse
import com.example.oniki.api.RetrofitClient
import com.example.oniki.databinding.ActivitySignInBinding
import com.example.oniki.storage.SharedPrefManager
import kotlinx.android.synthetic.main.activity_sign_in.*
import retrofit2.Call
import retrofit2.Response
import java.util.regex.Pattern

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn2.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty()) {
                etEmail.error = "Email required!!"
                etEmail.requestFocus()
                return@setOnClickListener
            } else if (!isValidEmail(email)) {
                etEmail.error = "Email Invalid!!"
                etEmail.requestFocus()
                return@setOnClickListener
            } else {
                etEmail.error = null
            }

            if (password.isEmpty()) {
                etPassword.error = "Password required!!"
                etPassword.requestFocus()
                return@setOnClickListener
            } else if (!isValidPasswordFormat(password)) {
                etPassword.error = "At least 8 letters!!"
                etPassword.requestFocus()
                return@setOnClickListener
            } else {
                etPassword.error = null
            }

            RetrofitClient.instance.userLogin(email, password)
                .enqueue(object : retrofit2.Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(applicationContext, "welcome ${response.body()?.data?.user?.first_name}", Toast.LENGTH_LONG).show()

                            SharedPrefManager.getInstance(applicationContext).setUser(response.body()?.data!!.user)

                            val intent = Intent(this@SignInActivity, ProfileActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Invalid Email or Password!!",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            "Not connected to server!!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }


        tvRegister.setOnClickListener {
            Intent(this, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }

    }

    override fun onStart() {
        super.onStart()

        if (SharedPrefManager.getInstance(this).isLoggedIn) {

            val intent = Intent(applicationContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile(
            "^" +
                    ".{8,}" +               //at least 8 characters
                    "$"
        );
        return passwordREGEX.matcher(password).matches()
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}