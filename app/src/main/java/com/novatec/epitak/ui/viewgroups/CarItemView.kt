package com.novatec.epitak.ui.viewgroups

import androidx.core.view.isVisible
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.epitak.R
import com.novatec.epitak.ui.addcar.MyItemClickListener
import com.novatec.epitak.util.loadRounded
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_car.view.*

class CarItemView(val car: CarDetails, val onItemClickListener: MyItemClickListener) : Item() {


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.carAvatar.loadRounded(car.image!!.link!!)
        viewHolder.itemView.plateNumber.text = car.carNumber
//        viewHolder.itemView.plateNumber.setTextFuture(
//            PrecomputedTextCompat.getTextFuture(car.carNumber!!,
//                                                viewHolder.itemView.plateNumber.textMetricsParamsCompat,
//                                                null)
//        )
//        viewHolder.itemView.plateNumber.text = car.carNumber
//        viewHolder.itemView.carYear.text = car.carYear.toString()
        viewHolder.itemView.carModel.text = car.carModel!!.name.toString()
        viewHolder.itemView.carAction.setOnClickListener {
            onItemClickListener.onClick(position, it)
        }

        viewHolder.itemView.selectedStroke.isVisible = car.def

//        if (car.airConditioner != null && car.airConditioner!!) {
//            viewHolder.itemView.checkboxAC.visibility = View.VISIBLE
//            viewHolder.itemView.checkboxAC.isChecked = car.airConditioner!!
//        } else {
//            viewHolder.itemView.checkboxAC.visibility = View.INVISIBLE
//        }

    }

    override fun getLayout() = R.layout.item_car

}
