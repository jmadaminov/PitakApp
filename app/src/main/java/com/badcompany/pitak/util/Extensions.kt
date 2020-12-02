package com.badcompany.pitak.util

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


/**
 * Created by jahon on 22-May-20
 */
val <T> LiveData<T>.valueNN
    get() = this.value!!

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

fun ImageView.loadImageUrl(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(this.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    Glide.with(this.context).load(url).placeholder(circularProgressDrawable)
        .apply(RequestOptions().centerInside()).into(this)
}

fun ImageView.loadCircleImageUrl(url: String) {
    Glide.with(this.context).load(url).apply(RequestOptions().circleCrop()).into(this)
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

