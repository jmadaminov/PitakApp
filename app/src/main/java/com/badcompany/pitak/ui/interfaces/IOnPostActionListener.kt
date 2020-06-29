package com.badcompany.pitak.ui.interfaces

import com.badcompany.domain.domainmodel.DriverPost

interface IOnPostActionListener {
    fun onModifyClick(post: DriverPost)
    fun onCancelClick(post: DriverPost)
}