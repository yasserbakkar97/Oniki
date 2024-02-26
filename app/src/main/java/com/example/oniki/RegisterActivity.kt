package com.example.oniki

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.oniki.api.DefaultResponse
import com.example.oniki.api.RegisterResponse
import com.example.oniki.api.RetrofitClient
import com.example.oniki.databinding.ActivityRegisterBinding
import com.example.oniki.storage.SharedPrefManager
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ProgressBar
        progressBar.progress = 33

        binding.clFirst.slideUp(700L, 0L)


        binding.btnNext.setOnClickListener {

            val firstName = firstNameEditText.text.toString().trim()
            val lastName = lastNameEditText.text.toString().trim()
            val email = emailPersonalEditText.text.toString().trim()
            val call = callEditText.text.toString().trim()

//            if (ivAddPhoto.drawable == null) {
//                Snackbar.make(btnNext,"You have to choose a photo",Snackbar.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
            if (firstName.isEmpty()) {
                firstNameEditText.error = "First Name required"
                firstNameEditText.requestFocus()
                return@setOnClickListener
            }
            if (lastName.isEmpty()) {
                lastNameEditText.error = "Last Name required"
                lastNameEditText.requestFocus()
                return@setOnClickListener
            }
            if (!isValidEmail(email)) {
                emailPersonalEditText.error = "Email Invalid!!"
                emailPersonalEditText.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                emailPersonalEditText.error = "Email required"
                emailPersonalEditText.requestFocus()
                return@setOnClickListener
            }
            if (call.isEmpty()) {
                callEditText.error = "Phone number required"
                callEditText.requestFocus()
                return@setOnClickListener
            }

            progressBar.progress = progressBar.progress + 33
            progressBar.max = 99

            clFirst.visibility = View.INVISIBLE
            clSecond.visibility = View.VISIBLE
            binding.clSecond.slideUp(700L, 0L)

            btnNext.visibility = View.INVISIBLE
            btnNext2.visibility = View.VISIBLE
            btnPrevious.visibility = View.VISIBLE


        }

        binding.btnNext2.setOnClickListener {
            progressBar.progress = progressBar.progress + 33
            progressBar.max = 99

            clFirst.visibility = View.INVISIBLE
            clSecond.visibility = View.INVISIBLE
            clThird.visibility = View.VISIBLE
            binding.clThird.slideUp(700L, 0L)


            btnNext.visibility = View.INVISIBLE
            btnNext2.visibility = View.INVISIBLE
            btnRegister_password.visibility = View.VISIBLE
            btnPrevious.visibility = View.INVISIBLE
            btnPrevious2.visibility = View.VISIBLE
        }



        binding.btnPrevious.setOnClickListener {
            progressBar.progress = progressBar.progress - 33
            progressBar.max = 99
            binding.clFirst.slideUp(700L, 0L)
            clFirst.visibility = View.VISIBLE
            clSecond.visibility = View.INVISIBLE
            clThird.visibility = View.INVISIBLE

            btnNext.visibility = View.VISIBLE
            btnNext2.visibility = View.INVISIBLE
            btnPrevious.visibility = View.INVISIBLE

        }

        binding.btnPrevious2.setOnClickListener {
            progressBar.progress = progressBar.progress - 33
            progressBar.max = 99
            binding.clSecond.slideUp(700L, 0L)
            clFirst.visibility = View.INVISIBLE
            clSecond.visibility = View.VISIBLE
            clThird.visibility = View.INVISIBLE

            btnPrevious2.visibility = View.INVISIBLE
            btnNext2.visibility = View.VISIBLE
            btnPrevious.visibility = View.VISIBLE
            btnNext.visibility = View.INVISIBLE
            btnRegister_password.visibility = View.INVISIBLE

        }

        tvSignin.setOnClickListener {
            Intent(this, SignInActivity::class.java).also {
                startActivity(it)
            }
        }
        tvSignin2.setOnClickListener { tvSignin.performClick() }
        tvSignin3.setOnClickListener { tvSignin.performClick() }

        binding.ivAddPhoto.setOnClickListener {
//            Intent(Intent.ACTION_GET_CONTENT).also {
//                it.type = "image/*"
//                startActivityForResult(it, 0)
//            }
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()


        }

        binding.btnRegisterPassword.setOnClickListener {
            val first_name = firstNameEditText.text.toString().trim()
            val last_name = lastNameEditText.text.toString().trim()
            val email = emailPersonalEditText.text.toString().trim()
            val phone_number = callEditText.text.toString().trim()
            val companyName = companyNameEditText.text.toString().trim()
            val title = titleEditText.text.toString().trim()
            val sector = sectorEditText.text.toString().trim()
            val www = wwwEditText.text.toString().trim()
            val which = whichEditText.text.toString().trim()
            val password = password2EditText.text.toString().trim()
            val password_confirmation = passwordAgainEditText.text.toString().trim()

            //     val accept_contact = checkBox.isChecked.toString()
            val accept_contact = cbConditions.isChecked

            if (password.isEmpty()) {
                password2EditText.error = "Password required"
                password2EditText.requestFocus()
                return@setOnClickListener
            } else if (!isValidPasswordFormat(password)) {
                password2EditText.error = "At least 8 letters!!"
                password2EditText.requestFocus()
                return@setOnClickListener
            } else {
                password2EditText.error = null
            }
            if (password_confirmation.isEmpty()) {
                passwordAgainEditText.error = "Password required"
                passwordAgainEditText.requestFocus()
                return@setOnClickListener
            } else if (!isValidPasswordFormat(password)) {
                passwordAgainEditText.error = "At least 8 letters!!"
                passwordAgainEditText.requestFocus()
                return@setOnClickListener
            } else {
                passwordAgainEditText.error = null
            }
            if (!password2EditText.text.toString().equals(passwordAgainEditText.text.toString())) {
                passwordAgainEditText.error = "Password does not match"
            } else if (!accept_contact) {
                Toast.makeText(this, "You have to agree to the terms ", Toast.LENGTH_LONG).show()

            } else {

                //   RetrofitClient.instance.register2(first_name, last_name, email, phone_number, password, password_confirmation, accept_contact)

                RetrofitClient.instance.register(
                    transformRequestBody(first_name),
                    transformRequestBody(last_name),
                    transformRequestBody(email),
                    transformRequestBody(phone_number),
                    transformRequestBody(password),
                    transformRequestBody(password_confirmation),
                    transformRequestBody(1)!!,
                )
                    .enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            if (response.isSuccessful) {

                                Toast.makeText(
                                    applicationContext,
                                    "User ${response.body()?.data?.first_name} Created successfully",
                                    Toast.LENGTH_LONG
                                ).show()

                                response.body()?.data?.let {
                                    SharedPrefManager.getInstance(applicationContext).setUser(it)
                                }


                                val intent =
                                    Intent(applicationContext, ActivationActivity::class.java)
                                intent.putExtra("email", email)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)

                            } else {

                                val errorBody = response.errorBody()?.string()
                                val gson = Gson()
                                try {
                                    try {
                                        val errorModel =
                                            gson.fromJson(errorBody, ErrorModel::class.java)
                                        if (!errorModel.errors.isNullOrEmpty()) {
                                            Toast.makeText(
                                                this@RegisterActivity,
                                                errorModel.errors[0].detail,
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    } catch (error: Exception) {
                                        Toast.makeText(
                                            this@RegisterActivity,
                                            error.message.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } catch (error: Exception) {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        error.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(applicationContext, t.message!!, Toast.LENGTH_LONG)
                                .show()
                        }
                    })
            }
        }
    }

    //    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
//            val uri = data?.data
//            ivAddPhoto.setImageURI(uri)
//        }
//    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions
            ivAddPhoto.setImageURI(uri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        }  else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    fun transformRequestBody(text: String): RequestBody {
        return text.toRequestBody("multipart/form-data".toMediaType())
    }

    fun transformRequestBody(number: Int?): RequestBody? {
        number?.let {
            return it.toString().toRequestBody("multipart/form-data".toMediaType())
        }
        return null
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPasswordFormat(password: String): Boolean {
        val passwordREGEX = Pattern.compile(
            "^" +
                    ".{8,}" +               //at least 8 characters
                    "$"
        );
        return passwordREGEX.matcher(password).matches()
    }


}

