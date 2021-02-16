package com.badcompany.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Representation for a [CarModel] fetched from the API
 */
data class CarModel(
    @SerializedName("id")
    var id: Long? = null,
    @SerializedName("modelId")
    var modelId: Long? = null,
    @SerializedName("imageId")
    var imageId: Long? = null,
    @SerializedName("fuelType")
    var fuelType: String? = null,
    @SerializedName("colorId")
    var colorId: Long? = null,
    @SerializedName("carNumber")
    var carNumber: String? = null,
    @SerializedName("carYear")
    var carYear: Int? = null,
    @SerializedName("airConditioner")
    var airConditioner: Boolean? = null,
    @SerializedName("def")
    var def: Boolean? = null,
    @SerializedName("imageList")
    var imageList: List<PhotoUploadModel>? = null)