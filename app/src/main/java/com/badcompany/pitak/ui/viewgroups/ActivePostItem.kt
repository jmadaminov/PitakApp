package com.badcompany.pitak.ui.viewgroups

import com.badcompany.domain.domainmodel.DriverPost
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.interfaces.IOnPostActionListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

class ActivePostItem(post: DriverPost, onPostActionListener: IOnPostActionListener) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {


    }

    override fun getLayout() = R.layout.item_active_post
}
