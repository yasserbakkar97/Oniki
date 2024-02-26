package com.example.oniki.api

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface Api {

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") email:String,
        @Field("password") password: String
    ): Call<LoginResponse>


//    @POST("https://eoabbq8tmeu0fkk.m.pipedream.net/")
    @Multipart
    @POST("register")
    fun register(
        @Part("first_name") first_name:RequestBody,
        @Part("last_name") last_name:RequestBody,
        @Part("email") email:RequestBody,
        @Part("phone_number") phone_number:RequestBody,
        @Part("password") password:RequestBody,
        @Part("password_confirmation") password_confirmation:RequestBody,
        @Part("accept_contact") accept_contact:RequestBody
//    @Body code: RegisterBody
    ):Call<RegisterResponse>

    @FormUrlEncoded
    @POST("register")
    fun register2(
        @Field("first_name") first_name:String,
        @Field("last_name") last_name:String,
        @Field("email") email:String,
        @Field("phone_number") phone_number:String,
        @Field("password") password:String,
        @Field("password_confirmation") password_confirmation:String,
        @Field("accept_contact") accept_contact:Boolean
//    @Body code: RegisterBody
    ):Call<DefaultResponse>

  @POST("activate")
    fun activateEmail(
      @Body activationRequestEnvelope: ActivationRequestEnvelope
  ): Call<DataEnvelope>

  @POST("resend-code")
   fun resendActivateCode(
      @Body email: EmailEnvelope
  ): Call<DataEnvelope>

}