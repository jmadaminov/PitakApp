package com.novatec.remote

import com.novatec.remote.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Defines the abstract methods used for interacting with the Bufferoo API
 */
interface AuthorizedApiService {

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("passenger_post/action/filter")
    suspend fun filterPassengerPost(
        @Body filter: FilterModel,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10): PassengerPostsResponse

    //END PASSENGER POST API

    //DRIVER POST API
    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("driver_post/action")
    suspend fun createPost(@Body driverPostBody: DriverPostModel): RespFormatter<DriverPostModel>

    //DRIVER POST API
    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("driver_post/action/{identifier}")
    suspend fun editPost(@Path(value = "identifier",
                               encoded = true) identifier: Long,
                         @Body driverPostBody: DriverPostModel): RespFormatter<DriverPostModel?>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("driver_post/action/cancel/{identifier}")
    suspend fun deletePost(@Path(value = "identifier",
                                 encoded = true) identifier: String): PlainResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("driver_post/action/finish/{identifier}")
    suspend fun finishPost(
        @Path(value = "identifier", encoded = true) identifier: String): PlainResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("driver_post/action/active")
    suspend fun getActivePosts(): DriverActivePostsResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("driver_post/action/history")
    suspend fun getHistoryPosts(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 10): DriverHistoryPostsResponse

    //


    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("feedback/action")
    suspend fun sendFeedback(@Body body: FeedbackBody): RespFormatter<Any>

    //Places Feed

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("region/action")
    suspend fun getPlacesFeed(
        @Query("query") query: String): PlaceListResponse
    //PLACES END


    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("car/action")
    suspend fun getCars(/*@Header("Content-Language") lang: String,*/): CarListResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("car/action")
    suspend fun createCar(/*@Header("Content-Language") lang: String,*/

        @Body car: CarModel): RespFormatter<CarModel>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("car/action/{identifier}")
    suspend fun updateCar(@Path(value = "identifier", encoded = true) identifier: Long,
                          @Body car: CarModel): RespFormatter<CarModel>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @DELETE("car/action/{identifier}")
    suspend fun deleteCar(@Path(value = "identifier",
                                encoded = true) identifier: Long): CarListResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("car/action/{identifier}/def")
    suspend fun setDefaultCar(@Path(value = "identifier", encoded = true) identifier: Long,
                              @Body carDefault: CarDefaultBody = CarDefaultBody()): PlainResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("car_model/action")
    suspend fun getCarModels(): CarModelsResponse

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("car_color/action")
    suspend fun getCarColors(): CarColorsResponse

    //END CAR API


    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("offer/driver/post/{id}")
    suspend fun getOffersForPost(@Path(value = "id", encoded = true) id: Long,
                                 @Query("page") page: Int = 0,
                                 @Query("size") size: Int = 10
    ): RespFormatter<List<OfferDTO>>


    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("driver_post/action/{id}")
    suspend fun getDriverPostById(@Path(value = "id",
                                        encoded = true) id: Long): RespFormatter<DriverPostModel>


    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("passenger_post/action/{id}/as_driver")
    suspend fun getPassengerPostById(@Path(value = "id",
                                           encoded = true) id: Long): RespFormatter<PassengerPostModel>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("offer/main/accept/{id}")
    suspend fun acceptOffer(@Path(value = "id", encoded = true) id: Long): RespFormatter<String?>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("driver_post/action/start/{id}")
    suspend fun startTrip(@Path(value = "id", encoded = true) id: Long): RespFormatter<String?>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("offer/main/reject/{id}")
    suspend fun rejectOffer(@Path(value = "id", encoded = true) id: Long): RespFormatter<String?>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("offer/main/cancel/{id}")
    suspend fun cancelMyOffer(@Path(value = "id", encoded = true) id: Long): RespFormatter<String?>

    @Headers("Content-Type:application/json", "Accept: application/json")
    @POST("offer/driver/action")
    suspend fun offerARide(@Body myOfferBody: DriverOfferBody): RespFormatter<Any>


    @Headers("Content-Type:application/json", "Accept: application/json")
    @PUT("prof/action/detail/mb")
    suspend fun updateUserInfo(@Body reqUpdateProfileInfo: ReqUpdateProfileInfo): RespFormatter<Any>


    @Headers("Content-Type:application/json", "Accept: application/json")
    @DELETE("driver_post/action/{postId}/passenger/{passengerId}")
    suspend fun removePassengerFromPost(@Path(value = "postId", encoded = true) postId: Long,
                                        @Path(value = "passengerId",
                                              encoded = true) passengerId: Long):RespFormatter< DriverPostModel>


//    @Headers("Content-Type:application/json", "Accept: application/json")0
//    @GET("passenger_post/action/{id}")
//    suspend fun getPassengerPostById(@Path(value = "id",
//                                           encoded = true) id: Long): RespFormatter<PassengerPostModel>
}

