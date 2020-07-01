package com.badcompany.pitak.ui.interfaces

import android.view.View
import com.badcompany.domain.domainmodel.DriverPost

interface IOnPostActionListener {
    fun onEditClick(post: DriverPost)
    fun onCancelClick(position:Int,post: DriverPost, parentView: View)
    fun onDoneClick(position:Int,post: DriverPost, parentView: View)
}