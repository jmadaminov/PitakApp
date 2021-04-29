package com.novatec.pitak.util

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.novatec.pitak.util.SizeUtils.dpToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay


/**
 * Created by jahon on 22-May-20
 */
val <T> LiveData<T>.valueNN
    get() = this.value!!

fun CoroutineScope.launchPeriodicAsync(
    repeatMillis: Long,
    maxMillis: Long,
    action: (Long) -> Unit
) = this.async {
    if (repeatMillis > 0 && maxMillis > 0 && repeatMillis < maxMillis) {
        var tempMillis = 0L
        while (repeatMillis < maxMillis) {
            action(maxMillis - tempMillis)
            delay(repeatMillis)
            tempMillis += repeatMillis
        }
    } else {
        action(0)
    }
}

fun Uri.getRealPathFromURI(context: Context): String? {
    var result = this.path
    val cursor: Cursor? =
        context.contentResolver.query(this, null, null, null, null)
    if (cursor == null) { // Source is Dropbox or other similar local file path
        result = this.path
    } else {
        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
        }
        cursor.close()
    }
    return result
}


fun ImageView.load(url: String) {
    Glide.with(this.context).load(url).into(this)
}

fun ImageView.loadRound(url: String) {
    Glide.with(this.context).load(url).apply(RequestOptions().circleCrop()).into(this)
}

fun ImageView.loadRounded(url: String, radius: Int = 10) {
    Glide.with(this.context).load(url)
        .transform(CenterCrop(), RoundedCorners(dpToPx(this.context, radius).toInt())).into(this)
}

fun ImageView.load(bitmap: Bitmap) {
    Glide.with(this.context).load(bitmap).into(this)
}
fun ImageView.loadRound(bitmap: Bitmap) {
    Glide.with(this.context).load(bitmap).apply(RequestOptions().circleCrop()).into(this)
}


fun View.hideKeyboard() {
    val imm = this.context!!.getSystemService(Context.INPUT_METHOD_SERVICE)!! as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard() {
    val imm = this.context!!.getSystemService(Context.INPUT_METHOD_SERVICE)!! as InputMethodManager
    imm.toggleSoftInputFromWindow(this.applicationWindowToken, InputMethodManager.SHOW_FORCED, 0)
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

///**
// * Extension function to simplify setting an afterTextChanged action to EditText components.
// */
//fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
//    this.addTextChangedListener(object : TextWatcher {
//        override fun afterTextChanged(editable: Editable?) {
//            afterTextChanged.invoke(editable.toString())
//        }
//
//        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//    })
//}

