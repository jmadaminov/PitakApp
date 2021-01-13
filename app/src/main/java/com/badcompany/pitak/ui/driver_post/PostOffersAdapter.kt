package com.badcompany.pitak.ui.driver_post

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.interfaces.IOnOfferActionListener
import com.badcompany.pitak.util.loadCircleImageUrl
import com.badcompany.remote.model.OfferDTO
import kotlinx.android.synthetic.main.item_offer.view.*
import java.text.DecimalFormat

class PostOffersAdapter(val onOfferActionListener: IOnOfferActionListener) :
    PagingDataAdapter<OfferDTO, PostOffersAdapter.OfferViewHolder>(OFFER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)
        return OfferViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem, onOfferActionListener)
    }

    class OfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(offer: OfferDTO, onOfferActionListener: IOnOfferActionListener) {
            itemView.apply {

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
                        context.getString(R.string.your_price)
                }

                tvName.text = offer.profileDTO.name + " " + offer.profileDTO.surname
                offer.profileDTO.image?.let {
                    it.link?.let { link ->
                        ivAvatar.loadCircleImageUrl(link)
                    }
                }

//                when (offer.status) {
//                    EOfferStatus.ACTIVE -> {
//                        tvStatus.setBackgroundResource(R.color.colorPrimaryDarkOpacityFifty)
//                        ivAccept.visibility = View.VISIBLE
//                        ivPhone.visibility = View.INVISIBLE
//                    }
//                    EOfferStatus.ACCEPTED -> {
//                        ivAccept.visibility = View.INVISIBLE
//                        ivPhone.visibility = View.VISIBLE
//                        tvStatus.setBackgroundResource(R.color.green)
//                    }
//                    EOfferStatus.REJECTED -> {
//                        ivAccept.visibility = View.INVISIBLE
//                        ivPhone.visibility = View.INVISIBLE
//                        tvStatus.setBackgroundResource(R.color.colorAccent)
//                    }
//                }.exhaustive

                ivDeny.setOnClickListener {
                    onOfferActionListener.onCancelClick(offer)
                }
                ivAccept.setOnClickListener {
                    onOfferActionListener.onAcceptClick(offer)
                }
//                ivPhone.setOnClickListener {
//                    onOfferActionListener.onPhoneCallClick(offer)
//                }
            }
        }
    }

    companion object {

        private val OFFER_COMPARATOR = object : DiffUtil.ItemCallback<OfferDTO>() {
            override fun areItemsTheSame(oldItem: OfferDTO, newItem: OfferDTO) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: OfferDTO, newItem: OfferDTO) =
                oldItem == newItem

        }
    }

}