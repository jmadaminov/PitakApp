package com.badcompany.pitak.util

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.badcompany.pitak.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by jahon on 22-May-20
 */


fun ImageView.loadImageUrl(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(this.context).load(url).placeholder(circularProgressDrawable).apply(RequestOptions().centerInside()).into(this)
}

fun ImageView.loadCircleImageUrl(url: String) {
    Glide.with(this.context).load(url).apply(RequestOptions().circleCrop()).into(this)
}

fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}