package com.dan.jamiicfapp.data.network

import com.dan.jamiicfapp.data.network.jcaresponse.AuthResponse
import com.dan.jamiicfapp.data.network.jcaresponse.DisabilitycaseResponse
import com.dan.jamiicfapp.data.network.jcaresponse.addDisabiltycaseResponse.DisabilityCaseAddedResponse
import com.dan.jamiicfapp.data.network.jcaresponse.commentresponse.CommentByIdResponse
import com.dan.jamiicfapp.data.network.jcaresponse.commentresponse.CommentResponse
import com.dan.jamiicfapp.data.network.jcaresponse.donationresponse.DonationResponse
import com.dan.jamiicfapp.data.network.jcaresponse.donationresponse.getdonation.GetListDonationResponse
import com.dan.jamiicfapp.data.network.jcaresponse.editdisability.UpdateDisabilityResponse
import com.dan.jamiicfapp.data.network.jcaresponse.feedbackresponse.FeedbackResponse
import com.dan.jamiicfapp.data.network.jcaresponse.feedbackresponse.GetFeedbackResponseAll
import com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.getrecord.FetchRecordCasesResponse
import com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.postrecord.RecordCaseResponse
import com.dan.jamiicfapp.data.network.requestbodys.AddCaseRecord
import com.dan.jamiicfapp.data.network.requestbodys.AddCaseRecordApprove
import com.dan.jamiicfapp.data.network.requestbodys.AddDisabilityCase
import com.dan.jamiicfapp.data.network.requestbodys.RegisterDetails
import com.google.gson.GsonBuilder
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface JcaApiService {

    /**
     * Authentication Api
     */
    @FormUrlEncoded
    @POST("login")
    suspend fun login_Api(
        @Field("phonenumber") phonenumber: String,
        @Field("password") password: String
    ): Response<AuthResponse>


    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun registerUser(@Body registerDetails: RegisterDetails): Response<AuthResponse>

    /**
     * Disabled Cases Api
     */
    @GET("getdisabilitycase")
    suspend fun viewpwds(): Response<DisabilitycaseResponse>

    @Multipart
    @POST("storedisabilitycase")
    suspend fun postDisabilityCase(
        @Part("disability_case") disability_case: RequestBody,
        @Part("description") description: RequestBody,
        @Part("amount_required") amount_required: RequestBody,
        @Part image: MultipartBody.Part

    ): Response<DisabilityCaseAddedResponse>

//UPDATING DISABILITY CASE
    @Multipart
    @POST("editdisabilitycase/{updateId}")
    suspend fun updateDisabilityCase(
        @Path("updateId") updateId: Int,
        @Part("disability_case") disability_case: RequestBody,
        @Part("description") description: RequestBody,
        @Part("amount_required") amount_required: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<UpdateDisabilityResponse>
    /**
     * Report Case Api
     */
    @Headers("Content-Type: application/json")
    @POST("storeRecordedcase")
    suspend fun storeRecordedcase(
        @Body addCaseRecord: AddCaseRecord
    ): Response<RecordCaseResponse>

    //@FormUrlEncoded
    @Headers("Content-Type: application/json")
    @PATCH("approve/{postNumber}")
    suspend fun updateRecordedcase(
        @Path("postNumber") recordId: Int,
        @Body addCaseRecordApprove: AddCaseRecordApprove
    ): Response<RecordCaseResponse>

    @GET("getRecordedcases")
    suspend fun getRecordedcases(): Response<FetchRecordCasesResponse>

    /**
     * Donation Api
     */
    @FormUrlEncoded
    @POST("donate")
    suspend fun donate(
        @Field("dcase_foreignid") dcase_foreignid: String,
        @Field("member_community_id") member_community_id: String,
        @Field("phonenumber") phonenumber: String,
        @Field("amount_donated") amount_donated: String
    ): Response<DonationResponse>



    @GET("getDonations")
    suspend fun getDonations():Response<GetListDonationResponse>

    /**
     * Feedback Api
     */
    @FormUrlEncoded
    @POST("feedback")
    suspend fun feedbackApi(
        @Field("member_community_id") member_community_id: String,
        @Field("feedback") feedback: String
    ): Response<FeedbackResponse>


    @GET("getFeedback")
    suspend fun getFeeback(): Response<GetFeedbackResponseAll>


    /**
     * Comment Api
     */
    @FormUrlEncoded
    @POST("comment")
    suspend fun comment(
        @Field("pwd_id") pwd_id: String,
        @Field("member_community_id") member_community_id: String,
        @Field("comment") comment: String
    ): Response<CommentResponse>


    @FormUrlEncoded
    @POST("commentID")
    suspend fun getCommentByPwdID(
        @Field("pwd_id") pwd_id: String
    ): Response<CommentByIdResponse>


    /**
     * Working of api request
     */
    companion object {
        operator fun invoke(networkInterceptor: NetworkInterceptor): JcaApiService {
            val logging = HttpLoggingInterceptor()
            logging.apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)

            }

            val okHttpClient = OkHttpClient.Builder().apply {
                addInterceptor(networkInterceptor)
                addInterceptor(logging)
            }.build()


            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .baseUrl("http://192.168.43.248/laravel-projects/project3JCA/public/api/")
                .build()
                .create(JcaApiService::class.java)

            return retrofit
        }
    }
}