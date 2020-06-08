package com.badcompany.pitak.ui.viewgroups

import com.badcompany.domain.domainmodel.CarDetails
import com.badcompany.pitak.R
import com.badcompany.pitak.util.loadImageUrl
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_car.view.*

class CarItemView(val car: CarDetails, val onItemClickListener: OnItemClickListener) : Item() {


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.carAvatar.loadImageUrl(car.image!!.link!!)
        viewHolder.itemView.plateNumber.text = car.carNumber
        viewHolder.itemView.carYear.text = car.carYear.toString()
        viewHolder.itemView.carModel.text = car.carNumber

    }

    override fun getLayout() = R.layout.item_car

}
