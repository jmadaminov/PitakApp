package com.badcompany.pitak.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by jahon on 22-May-20
 */


fun ImageView.loadImageUrl(url: String) {
    Glide.with(this.context).load(url).into(this)
}

fun ImageView.loadCircleImageUrl(url: String) {
    Glide.with(this.context).load(url).apply(RequestOptions().circleCrop()).into(this)
}