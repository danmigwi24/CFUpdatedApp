package com.dan.jamiicfapp.data.network

import com.dan.jamiicfapp.data.network.jcaresponse.donationresponse.DonationResponse
import com.dan.jamiicfapp.data.network.jcaresponse.AuthResponse
import com.dan.jamiicfapp.data.network.jcaresponse.PwdResponse
import com.dan.jamiicfapp.data.network.jcaresponse.eventresponse.EventsResponse
import com.dan.jamiicfapp.data.network.requestbodys.RegisterDetails
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface JcaApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun login_Api(
        @Field("phonenumber") phonenumber: String,
        @Field("password") password: String
    ): Response<AuthResponse>


    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun registerUser(@Body registerDetails: RegisterDetails): Response<AuthResponse>

    @GET("viewpwds")
    suspend fun viewpwds(): Response<PwdResponse>

    @GET("viewEvents")
    suspend fun viewEvents(): Response<EventsResponse>

    @FormUrlEncoded
    @POST("donate")
    fun donate(
        @Field("pwd_id") pwd_id: String,
        @Field("member_community_id") member_community_id: String,
        @Field("phonenumber") phonenumber: String,
        @Field("amount_donated") amount_donated: String
    ): Response<DonationResponse>


    companion object {
        operator fun invoke(networkInterceptor: NetworkInterceptor): JcaApiService {
            val logging = HttpLoggingInterceptor()
            logging.apply {
                setLevel(HttpLoggingInterceptor.Level.HEADERS)

            }

            val okHttpClient = OkHttpClient.Builder().apply {
                addInterceptor(networkInterceptor)
                addInterceptor(logging)
            }.build()

            val gson = GsonBuilder()
                .setLenient()
                .create()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl("http://192.168.43.248/laravel-projects/project3JCA/public/api/")
                .build()
                .create(JcaApiService::class.java)

            return retrofit
        }
    }
}