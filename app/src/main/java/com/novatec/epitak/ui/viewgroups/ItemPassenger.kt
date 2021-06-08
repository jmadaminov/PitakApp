package com.novatec.epitak.ui.viewgroups

import android.content.Intent
import android.net.Uri
import androidx.core.view.isVisible
import com.novatec.domain.domainmodel.Passenger
import com.novatec.epitak.R
import com.novatec.epitak.util.loadRound
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_passenger.view.*
import java.text.DecimalFormat


class ItemPassenger(val obj: Passenger,
                    val isHistoryPost: Boolean = false,
                    val onDeleteClick: (obj: Passenger) -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            tvName.text = obj.profile?.name + " " + obj.profile?.surname
            obj.offer?.priceInt?.let {
                tvAgreedPrice.text = context.getString(R.string.agreed_price,
                                                       DecimalFormat("#,###").format(obj.offer?.priceInt))
            }
            tvPersonCount.text = obj.offer?.seat?.toString()
            obj.profile?.image?.link?.let {
                ivAvatar.loadRound(it)
            } ?: run {
                ivAvatar.setImageResource(R.drawable.ic_baseline_account_circle_24)
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
