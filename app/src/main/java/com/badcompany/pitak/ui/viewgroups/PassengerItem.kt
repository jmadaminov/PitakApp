package com.badcompany.pitak.ui.viewgroups

import android.content.Intent
import android.net.Uri
import com.badcompany.domain.domainmodel.Passenger
import com.badcompany.pitak.R
import com.badcompany.pitak.util.loadCircleImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_passenger.view.*


class PassengerItem(val obj: Passenger, onDeleteClick: () -> Unit) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            tvName.text = obj.profile?.name + " " + obj.profile?.surname
            tvAgreedPrice.text = obj.offer?.priceInt?.toString()
            tvPersonCount.text = obj.offer?.seats?.toString()
            obj.profile?.image?.link?.let {
                ivAvatar.loadCircleImageUrl(it)
            }

            fabCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:+${obj.profile!!.phoneNum}")
                context.startActivity(intent)
            }

        }
    }

    override fun getLayout() = R.layout.item_passenger
}
