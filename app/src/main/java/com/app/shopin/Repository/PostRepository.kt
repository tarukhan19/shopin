package com.codingwithjks.kotlinflow.Repository


import com.app.shopin.Orders.models.TimeSlotListResponse

import com.customer.gogetme.Retrofit.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class PostRepository {

//    fun getPost() : Flow<Response<TimeSlotListResponse>> = flow {
//        val postList= ServiceBuilder.getApiService().getPost()
//            emit(postList)
//    }.flowOn(Dispatchers.IO)
}