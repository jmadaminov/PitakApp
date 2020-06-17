package com.badcompany.pitak.ui.viewgroups

import com.badcompany.domain.domainmodel.Place
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.addcar.MyItemClickListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_place_autocomplete.view.*

class PlaceAutocompleteItemView(val place: Place,
                                val onItemClickListener: MyItemClickListener? = null) : Item() {


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.placeName.text = place.nameRu

    }

    override fun getLayout() = R.layout.item_place_autocomplete

}
