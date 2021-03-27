package com.novatec.pitak.ui.viewgroups

import android.content.Intent
import android.net.Uri
import androidx.core.view.isVisible
import com.novatec.domain.domainmodel.Passenger
import com.novatec.pitak.R
import com.novatec.pitak.util.loadCircleImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_passenger.view.*
import java.text.DecimalFormat


class PassengerItem(val obj: Passenger,
                    val isHistoryPost: Boolean = false,
                    val onDeleteClick: (obj: Passenger) -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            tvName.text = obj.profile?.name + " " + obj.profile?.surname
            obj.offer?.priceInt?.let {
                tvAgreedPrice.text =
                    DecimalFormat("#,###").format(obj.offer?.priceInt) + " " + context.getString(R.string.sum)
            }
            tvPersonCount.text = obj.offer?.seat?.toString()
            obj.profile?.image?.link?.let {
                ivAvatar.loadCircleImageUrl(it)
            }

            fabCall.isVisible = !isHistoryPost
            ivDelete.isVisible = !isHistoryPost

            fabCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:+${obj.profile!!.phoneNum}")
                context.startActivity(intent)
            }
            ivDelete.setOnClickListener {
                onDeleteClick(obj)
            }

        }
    }

    override fun getLayout() = R.layout.item_passenger
}
