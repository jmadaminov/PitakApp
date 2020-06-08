package com.badcompany.pitak.ui.addcar

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.badcompany.domain.domainmodel.CarColorBody
import com.badcompany.pitak.R
import kotlinx.android.synthetic.main.item_car_color.view.*


class ColorsArrayAdapter(val context: Context,
                         val colors: List<CarColorBody>/*,
                         val clickListener: MyItemClickListener*/) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_car_color, parent, false)
            view.textColor.text = colors[position].nameEn
            view.imgColor.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(colors[position].hex))
         /*   view.itemParentView.setOnClickListener {
                clickListener.onClick(position)
            }*/
        } else {
            view = convertView
        }

        return view
    }

    override fun getItem(position: Int) = colors[position]
    override fun getItemId(position: Int) = 0L
    override fun getCount() = colors.size

}

interface MyItemClickListener {
    fun onClick(pos: Int)
}