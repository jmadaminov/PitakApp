package com.novatec.epitak.ui.viewgroups

import android.view.View
import com.novatec.domain.domainmodel.Offer
import com.novatec.epitak.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_parcel_offer.view.*
import java.text.DecimalFormat

class ItemParcelOffer(val offer: Offer,
                      val onAccept: (offer: Offer) -> Unit,
                      val onCancel: (offer: Offer) -> Unit) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {

            if (offer.message.isNullOrBlank()) {
                tvMessage.visibility = View.GONE
            } else {
                tvMessage.visibility = View.VISIBLE
                tvMessage.text = offer.message
            }

            offer.price?.also {
                tvOfferingPrice.text =
                    DecimalFormat("#,###").format(it) + " " + context.getString(R.string.sum)
            } ?: run {
                tvOfferingPrice.text =
                    context.getString(R.string.my_price)
            }

//            tvName.text = offer.profile.name + " " + offer.profile.surname
//            offer.profile.image?.let {
//                it.link?.let { link ->
//                    ivAvatar.loadRound(link)
//                }
//            } ?: run {
//                ivAvatar.setImageResource(R.drawable.ic_baseline_account_circle_24)
//            }

            ivDeny.setOnClickListener {
                onCancel(offer)
            }
            ivAccept.setOnClickListener {
                onAccept(offer)
            }

        }
    }

    override fun getLayout() = R.layout.item_parcel_offer
}
