package com.app.shopin.network

import com.app.shopin.UserAuth.model.EditProfileResponse
import com.app.shopin.UserAuth.model.LoadProfileResponse
import com.app.shopin.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    /// generate otp with email //

    @FormUrlEncoded
    @POST("users/generateotp/")
    fun registerUserEmail(@Field("email") email: String): Call<EmailResponse>

    /// generate otp with mobile //

    @FormUrlEncoded
    @POST("users/generateotp/")
    fun registerUserMobile(@Field("email") mobileno: String): Call<MobileResponse>

    // verify otp with email //

    @FormUrlEncoded
    @POST("users/verifyotp/")
    fun verifyUser(
        @Field("email") email: String,
        @Field("otp") otp: String
    ): Call<OtpResponse>


    /*................editprofile.........................*/
    @Multipart
    @POST("users/update_user_prfile/")
    fun editProfile(
        @Part("email") email: RequestBody,
        @Part("name") name: RequestBody,
        @Part("phone_no") mobileno: RequestBody,
        @Part multipartimage: MultipartBody.Part?
    ): Call<EditProfileResponse>


    //    // upload user store detail  //
//
//    @FormUrlEncoded
//    @POST("store-request/")
//    fun uploadGetMyStore(
//        @Field("name") name: String,
//        @Field("email") email: String,
//        @Field("mobile") mobile: String,
//        @Field("business_name") business_name: String,
//        @Field("business_address") business_address: String,
//        @Field("category") category: String,
//    ): retrofit2.Call<MessageResponse>
//
//    //  type of store //
//
//    @GET("category/get_store_category/")
//    fun fetchStoreCategory(): retrofit2.Call<StoreCategoryResponse>
//
//
////    @GET("store-request/{id}")
////    fun fetchStoreDetail(
////        @Path("id") id: String,
////    ): retrofit2.Call<FetchStoreResponse>
//
//    @GET("category/?")
//    fun searchCategory(
//        @Query("search_on") value: String,
//    ): retrofit2.Call<StoreCategoryResponse>
//
//    @GET("store/get_new_store/")
//     fun storeList(): retrofit2.Call<StoreListResponse>
//
//    @GET("store-request/")
//    fun fetchStoreDetail(
//    ): retrofit2.Call<StoreListResponse>
//
//    @FormUrlEncoded
//    @POST("user-store-map/save_default_store/")
//    fun saveDefaultStore(
//        @Field("store") store_id : Int,
//        @Field("map_type") map_type : Int
//    ): retrofit2.Call<MessageResponse>
//
//    @GET("user-store-map/default_store/")
//    fun fetchDefaultStore(
//    ): retrofit2.Call<DefaultStoreResponse>
//
//    @FormUrlEncoded
//    @POST("store/")
//    fun uploadStore(
//        @Field("name") name: String,
//        @Field("contact_email") contact_email: String,
//        @Field("contact_no") contact_no: String,
//        @Field("owner") owner: String,
//        @Field("address") address: String,
//        @Field("zipcode") zipcode: String,
//        @Field("operation_timing") operation_timing: String,
//        @Field("is_active") is_active: Boolean,
//        @Field("lattitude") lattitude: Float,
//        @Field("longitude") longitude: Float,
//        @Field("joining_date") joining_date: String,
//        @Field("approved_date") approved_date: String,
//        @Field("inactive_date") inactive_date: String,
//        @Field("service_type") service_type: String,
//        @Field("description") description: String,
//        @Field("pricing_policies") pricing_policies: String,
//        @Field("qrcode_text") qrcode_text: String,
//        @Field("pickup_timing") pickup_timing: String,
//        @Field("return_policy") return_policy: String,
//        @Field("terms_and_conditions") terms_and_conditions: String,
//    ): retrofit2.Call<UploadStoreResponse>
//
//    @GET("user-store-map/get_favourite_store/")
//    fun fetchFavouriteStore(
//    ): retrofit2.Call<DefaultStoreResponse>
//
//    @GET("user-store-map/save_favourite_store/")
//    fun saveStoreResponse(
//    ): retrofit2.Call<DefaultStoreResponse>
//
//    @GET("faq/")
//    fun fetchFrequentAskQuestion(
//    ): retrofit2.Call<DefaultStoreResponse>
//
//    @FormUrlEncoded
//    @POST("faq/")
//    fun uploadFrequentAskQuestion(
//        @Field("question") faq : String
//    ): retrofit2.Call<MessageResponse>
//
//    @FormUrlEncoded
//    @POST("delivery-address/")
//    fun uploadDeliveryAddress(
//        @Field("name") name : String,
//        @Field("address") address : String,
//        @Field("phone") phone : Long,
//        @Field("zip_code") zip_code : String,
//        @Field("apartment_building") apartment_building : String,
//        @Field("area") area : String,
//        @Field("landmark") landmark : String,
//        @Field("address_type") address_type : String?,
//        @Field("can_receive_on_sundays") can_receive_on_sundays : String,
//        @Field("can_receive_on_saturdays") can_receive_on_saturdays : String,
//        @Field("delivery_instruction") delivery_instruction : String
//    ): retrofit2.Call<MessageResponse>
//
    @GET("users/get_user_profile/")
    fun loadProfile(
    ): retrofit2.Call<LoadProfileResponse>

//    @FormUrlEncoded
//    @POST("user-location/")
//    fun saveAndUpdateLocation(
//        @Field("lattitude") lattitude: Float,
//        @Field("longitude") longitude: Float
//    ): retrofit2.Call<MessageResponse>
//
//    @GET("user-store-map/get_near_by_shop/")
//    fun getNearByLocation(): retrofit2.Call<NearByModelResponse>

}