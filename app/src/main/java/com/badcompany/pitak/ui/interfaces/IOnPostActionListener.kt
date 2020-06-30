package com.badcompany.pitak.ui.interfaces

import com.badcompany.domain.domainmodel.DriverPost

interface IOnPostActionListener {
    fun onEditClick(post: DriverPost)
    fun onCancelClick(post: DriverPost)
    fun onDoneClick(post: DriverPost)
}