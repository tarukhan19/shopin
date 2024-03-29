package com.app.shopin.network

import com.app.shopin.Orders.models.*
import com.app.shopin.UserAuth.model.EditProfileResponse
import com.app.shopin.UserAuth.model.LoadProfileResponse
import com.app.shopin.homePage.models.*
import com.app.shopin.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
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

    @GET("masters/category/get_store_category/")
    fun getCategoryData(): Call<StoreCategoryListResponse>

    @GET("store/list_store_category/")
    fun getSearchPageData(@Query("key")key : Int): Call<StoreCategoryListResponse>

    @GET("store/search_store/")
    fun getSearchListData(@Query("key")key : Int,@Query("search_on")search_on : String): Call<SearchListResponse>

    @GET("store/get_new_store/")
     fun getAllStoreListData(): Call<AllStoreListResponse>

    @GET("store/store_details/")
    fun fetchStoreDetail(@Query("id") id: String): Call<StoreDetailApiResponse>

    @FormUrlEncoded
    @POST("delivery-address/")
    fun addDeliveryAddress(
        @Field("name") name : String,
        @Field("address") address : String,
        @Field("address_type") address_type : String?,
        @Field("delivery_instruction") delivery_instruction : String,
        @Field("apartment_building") apartment_building : String,
        @Field("is_default") is_default : String,
        @Field("lattitude") latitude : Double,
        @Field("longitude") longitude : Double
    ): Call<DeliveryAddressAddResponse>

    @FormUrlEncoded
    @POST("delivery-address/update_delivery_address/")
    fun updateDeliveryAddress(
        @Field("id") id : String,
        @Field("name") name : String,
        @Field("address") address : String,
        @Field("address_type") address_type : String?,
        @Field("delivery_instruction") delivery_instruction : String,
        @Field("apartment_building") apartment_building : String,
        @Field("is_default") is_default : String,
        @Field("lattitude") latitude : Double,
        @Field("longitude") longitude : Double
    ): Call<DeliveryAddressUpdateResponse>

    @DELETE("delivery-address/delete_delivery_address/")
    fun removeAddress(@Query("id") id: String): Call<RemoveAddressResponse>

    @GET("users/get_user_profile/")
    fun loadProfile(
    ): Call<LoadProfileResponse>

    @GET("delivery-address/")
    fun getDeliveryAddressList(
    ): Call<DeliveryAddressListResponse>

    @GET("delivery-address/get_delivery_address/")
    fun getSingleDeliveryAddress(@Query("id") id: String): Call<SingleDeliveryAddressResponse>

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
        @Field("order_type")ordertype : String,
        @Field("price")price : String,
        @Field("quantity")quantity : String,
        @Field("is_update")is_update : String,
        @Field("inventory") inventory: String,
        @Field("tax_amount") tax_amount: String,
        @Field("total_amount")total_amount : String,
        @Field("tax_percentage")  tax_percentage: String,
        @Field("store")  store: String
    ): Call<AddToCartResponse>

    //remove_cart
    @DELETE("order/cart/remove_cart/")
    fun removeCart(@Query("inventory_id") id: String): Call<RemoveCartResponse>


    @GET("storeinventory/storeitem/inventory_details/")
    fun prodDetail(@Query("id") id: String): Call<ProductDetailResponse>

    @GET("order/cart/list_cart_items/")
    fun getCartList(): Call<CartListResponse>

//    @FormUrlEncoded
//    @POST("useror/der/order/place_order/")
//    fun placeOrder(
//
//    ): Call<PlaceOrderResponse>
//

    /*................placeoreder.........................*/
    @Headers("Content-Type: application/json")
    @POST("useror/der/order/place_order/")
    fun placeOrder(@Body placeOrderResponse: PlaceOrder): Call<PlaceOrderResponse>



    @GET("store/get_store_timeslot/")
    fun getTimeslotList(@Query("id") id: String): Call<TimeSlotListResponse>

    @GET("useror/der/order/order_list/")
    suspend fun getOrderList(@Query("months") selectDuration: String,@Query("status") selectOrderStatus: String): Response<OrderHistoryListResponse>

    @GET("useror/der/order/order_detail/")
    suspend fun getOrderDetails(@Query("order_id") id: String):OrderHistoryDetailsResponse

    @FormUrlEncoded
    @POST("order-review/user-store-reeview/save_store_rating/")
    suspend fun ratingReviewSubmit(
        @Field("store")id : String,
        @Field("order")order : String,
        @Field("rating")rating : String,
        @Field("comment")comment : String

    ): Response<RatingReviewResponse>

    @FormUrlEncoded
    @POST("useror/der/order/delivery_tip/")
    suspend fun tipSubmit(
        @Field("store")id : String,
        @Field("order")rating : String,
        @Field("comments")comment : String,
        @Field("amount")amount : String
    ): Response<TipResponse>

    @FormUrlEncoded
    @POST("useror/der/order/save_curbside_cumment/")
    suspend fun curbsidemsgSubmit(
        @Field("order_id")order_id : String,
        @Field("curbside_pickup_comment")comment : String
    ): Response<CurbsideMsgResponse>


    @GET("order-issue/get_list/")
   suspend fun issuewithitemdetail(@Query("order_no") order_no: String): Response<IssueWithItemResponse>

    @Headers("Content-Type: application/json")
    @POST("order-issue/create_issue/")
    suspend  fun createIssueWithItem(@Body sendSelectedIssue: SendSelectedIssue): Response<CreateIssueResponse>

}
