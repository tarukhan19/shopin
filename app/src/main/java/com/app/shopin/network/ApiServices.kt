package com.app.shopin.network

import com.app.shopin.UserAuth.model.EditProfileResponse
import com.app.shopin.UserAuth.model.LoadProfileResponse
import com.app.shopin.homePage.models.*
import com.app.shopin.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    /// generate otp with email //
//    https://shopinzip.cladev.com/api/v1.0/users/verifyotp/
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
        @Field("device_type")devicetype:String,
        @Field("device_id")device_id:String,
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

    @GET("masters/category/get_store_category/")
    fun getCategoryData(): Call<StoreCategoryListResponse>


    @GET("store/list_store_category/")
    fun getSearchPageData(@Query("key")key : String): Call<StoreCategoryListResponse>

    @GET("store/search_store/")
    fun getSearchListData(@Query("key")key : String,@Query("search_on")search_on : String): Call<SearchListResponse>


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
    @GET("store/get_new_store/")
     fun getAllStoreListData(): Call<AllStoreListResponse>

    @GET("store/store_details/")
    fun fetchStoreDetail(@Query("id") id: String): Call<StoreDetailApiResponse>

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
    @FormUrlEncoded
    @POST("delivery-address/")
    fun addDeliveryAddress(
        @Field("name") name : String,
        @Field("address") address : String,
        @Field("address_type") address_type : String?,
        @Field("delivery_instruction") delivery_instruction : String,
        @Field("apartment_building") apartment_building : String,
        @Field("is_default") is_default : String,
        @Field("latitude") latitude : Double,
        @Field("longitude") longitude : Double
    ): Call<DeliveryAddressAddResponse>

    @GET("users/get_user_profile/")
    fun loadProfile(
    ): Call<LoadProfileResponse>

    @GET("delivery-address/")
    fun getDeliveryAddressList(
    ): Call<DeliveryAddressListResponse>


    @FormUrlEncoded
    @POST("store-request/")
    fun getMyStoreListed(
        @Field("name")name : String,
        @Field("business_name")business_name : String,
        @Field("business_address")business_address : String,
        @Field("category") category: String,
        @Field("email") email: String,
        @Field("mobile")mobile : String,
        @Field("status")  status: String
    ): Call<GetMyStoreListedResponse>

    @FormUrlEncoded
    @POST("order/cart/save_cart/")
    fun addToCart(
        @Field("price")price : String,
        @Field("quantity")quantity : String,
        @Field("is_update")is_update : String,
        @Field("inventory") inventory: String,
        @Field("tax_amount") tax_amount: String,
        @Field("total_amount")total_amount : String,
        @Field("tax_percentage")  tax_percentage: String,
        @Field("store")  store: String
    ): Call<AddToCartResponse>

    @DELETE("order/cart/remoove_cart/")
    fun removeCart(@Query("inventory_id") id: String): Call<RemoveCartResponse>

    @GET("storeinventory/storeitem/inventory_details/")
    fun prodDetail(@Query("id") id: String): Call<ProductDetailResponse>

    @GET("order/cart/list_cart_items/")
    fun getCartList(): Call<CartListResponse>


}