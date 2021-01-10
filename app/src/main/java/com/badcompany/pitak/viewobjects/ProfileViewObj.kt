package com.badcompany.pitak.viewobjects

import android.os.Parcelable
import com.badcompany.data.model.ImageEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileViewObj(val phoneNum: String,
                          val name: String,
                          val surname: String,
                          val id: String,
                          val image: ImageViewObj? = null) : Parcelable