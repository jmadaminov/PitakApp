package com.novatec.pitak.ui.viewgroups

import android.view.View
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.pitak.R
import com.novatec.pitak.ui.addcar.MyItemClickListener
import com.novatec.pitak.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_car.view.*

class CarItemView(val car: CarDetails, val onItemClickListener: MyItemClickListener) : Item() {


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.carAvatar.loadImageUrl(car.image!!.link!!)
        viewHolder.itemView.plateNumber.text = car.carNumber
//        viewHolder.itemView.plateNumber.setTextFuture(
//            PrecomputedTextCompat.getTextFuture(car.carNumber!!,
//                                                viewHolder.itemView.plateNumber.textMetricsParamsCompat,
//                                                null)
//        )
//        viewHolder.itemView.plateNumber.text = car.carNumber
        viewHolder.itemView.carYear.text = car.carYear.toString()
        viewHolder.itemView.carModel.text = car.carModel!!.name.toString()
        viewHolder.itemView.carAction.setOnClickListener {
            onItemClickListener.onClick(position, it)
        }

        if (car.def != null && car.def!!) {
            viewHolder.itemView.cardParent.setBackgroundResource(R.drawable.stroke_primary)
        }

        if (car.airConditioner != null && car.airConditioner!!) {
            viewHolder.itemView.checkboxAC.visibility = View.VISIBLE
            viewHolder.itemView.checkboxAC.isChecked = car.airConditioner!!
        } else {
            viewHolder.itemView.checkboxAC.visibility = View.INVISIBLE
        }

    }

    override fun getLayout() = R.layout.item_car

}
