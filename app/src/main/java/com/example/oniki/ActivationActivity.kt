package com.example.oniki

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.oniki.api.*
import com.example.oniki.storage.SharedPrefManager
import com.goodiebag.pinview.Pinview
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_activation.*
import kotlinx.android.synthetic.main.activity_activation.tvSignin
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import retrofit2.Call
import retrofit2.Response

class ActivationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activation)

        pinview.requestFocus()

        btnConfirmActivation.setOnClickListener {
            val email = intent.getStringExtra("email")
            val code = pinview.value

            val pin = Pinview(this)
            pin.setPinViewEventListener { pinview, fromUser ->
                Toast.makeText(this@ActivationActivity, pinview!!.value, Toast.LENGTH_SHORT).show()
            }

            RetrofitClient.instance.activateEmail(ActivationRequestEnvelope(email , code))
                .enqueue(object : retrofit2.Callback<DataEnvelope> {
                    override fun onResponse(
                        call: Call<DataEnvelope>,
                        response: Response<DataEnvelope>
                    ) {
                        if (response.isSuccessful) {
                            val intent = Intent(applicationContext, ProfileActivity::class.java)
                            startActivity(intent)
                        } else {
                            val errorBody = response.errorBody()?.string()
                            val gson = Gson()
                            try {
                                try {
                                    val errorModel = gson.fromJson(errorBody, ErrorModel::class.java)
                                    if (!errorModel.errors.isNullOrEmpty()) {
                                        Toast.makeText(this@ActivationActivity,errorModel.errors[0].detail, Toast.LENGTH_SHORT).show()

                                    }
                                } catch (error: Exception) {
                                    Toast.makeText(this@ActivationActivity,error.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            } catch (error: Exception) {
                                Toast.makeText(this@ActivationActivity,error.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<DataEnvelope>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message!!, Toast.LENGTH_LONG).show()
                    }
                })

        }

        tvResendCode.setOnClickListener {
            val email = intent.getStringExtra("email")
            RetrofitClient.instance.resendActivateCode(EmailEnvelope(email))
                .enqueue(object : retrofit2.Callback<DataEnvelope> {
                    override fun onResponse(
                        call: Call<DataEnvelope>,
                        response: Response<DataEnvelope>
                    ) {
                        if (response.isSuccessful) {

                        } else {
                            val errorBody = response.errorBody()?.string()
                            val gson = Gson()
                            try {
                                try {
                                    val errorModel = gson.fromJson(errorBody, ErrorModel::class.java)
                                    if (!errorModel.errors.isNullOrEmpty()) {
                                        Toast.makeText(this@ActivationActivity,errorModel.errors[0].detail, Toast.LENGTH_SHORT).show()

                                    }
                                } catch (error: Exception) {
                                    Toast.makeText(this@ActivationActivity,error.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            } catch (error: Exception) {
                                Toast.makeText(this@ActivationActivity,error.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    override fun onFailure(call: Call<DataEnvelope>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message!!, Toast.LENGTH_LONG).show()
                    }
                })

            tvSignin.setOnClickListener {
                Intent(this, SignInActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }
}