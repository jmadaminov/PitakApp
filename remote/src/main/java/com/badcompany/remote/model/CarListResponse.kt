package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Created by jahon on 12-Apr-20
 */
data class CarListResponse(val code: Int? = null,
                           val message: String? = null,
                           val data: List<CarDetails>? = null)

data class CarDetails(
    @SerializedName("id")
    var id: Long? = null,
    @SerializedName("carModel")
    var carModel: IdNameBody? = null,
    @SerializedName("image")
    var image: ImageBody? = null,
    @SerializedName("fuelType")
    var fuelType: String? = null,
    @SerializedName("carColor")
    var carColor: CarColorModel? = null,
    @SerializedName("carNumber")
    var carNumber: String? = null,
    @SerializedName("carYear")
    var carYear: Int? = null,
    @SerializedName("airConditioner")
    var airConditioner: Boolean? = null,
    @SerializedName("def")
    var def: Boolean? = null,
    @SerializedName("imageList")
    var imageList: List<ImageBody>? = null)

data class IdNameBody(
    @SerializedName("id")
    var id: Long? = null,
    @SerializedName("name")
    var name: String? = null)

data class ImageBody(
    @SerializedName("id")
    var id: Long? = null,
    @SerializedName("link")
    var link: String? = null)
