package com.novatec.epitak.viewobjects

import android.os.Parcelable
import com.novatec.data.model.ImageEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileViewObj(val phoneNum: String,
                          val name: String,
                          val surname: String,
                          val id: Long,
                          val image: ImageViewObj? = null) : Parcelable