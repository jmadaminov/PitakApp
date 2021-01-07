//package com.badcompany.pitak.util
//
//import com.badcompany.domain.domainmodel.Filter
//import com.badcompany.remote.model.FilterModel
//import com.badcompany.remote.model.PassengerPostsResponse
//import retrofit2.http.Body
//import retrofit2.http.Headers
//import retrofit2.http.POST
//import retrofit2.http.Query
//
//interface AuthAPIServiceTEST {
//    @Headers("Content-Type:application/json", "Accept: application/json")
//    @POST("passenger_post/action/filter")
//    suspend fun filterPassengerPost(
//        @Body filter: Filter,
//        @Query("page") page: Int = 0,
//        @Query("size") size: Int = 10): PassengerPostsResponse
//}