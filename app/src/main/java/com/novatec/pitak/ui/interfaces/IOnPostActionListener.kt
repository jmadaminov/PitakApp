package com.novatec.pitak.ui.interfaces

import android.view.View
import com.novatec.domain.domainmodel.DriverPost

interface IOnPostActionListener {
    fun onEditClick(post: DriverPost)
    fun onCancelClick(position:Int,post: DriverPost, parentView: View)
    fun onDoneClick(position:Int,post: DriverPost, parentView: View)
}