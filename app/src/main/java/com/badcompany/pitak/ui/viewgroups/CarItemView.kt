package com.badcompany.pitak.ui.viewgroups

import androidx.core.content.ContextCompat
import com.badcompany.domain.domainmodel.CarDetails
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.addcar.MyItemClickListener
import com.badcompany.pitak.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_car.view.*

class CarItemView(val car: CarDetails, val onItemClickListener: MyItemClickListener) : Item() {


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.carAvatar.loadImageUrl(car.image!!.link!!)
        viewHolder.itemView.plateNumber.text = car.carNumber
        viewHolder.itemView.carYear.text = car.carYear.toString()
        viewHolder.itemView.carModel.text = car.carNumber
        viewHolder.itemView.carAction.setOnClickListener {
            onItemClickListener.onClick(position, it)
        }

        if (car.def != null && car.def!!) {
            viewHolder.itemView.cardParent.setCardBackgroundColor(ContextCompat.getColor(viewHolder.itemView.context,
                                                                                         R.color.orange))
        }

    }

    override fun getLayout() = R.layout.item_car

}
