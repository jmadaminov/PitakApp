package com.badcompany.pitak.ui.viewgroups

import com.badcompany.domain.domainmodel.Place
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.addpost.choosedestinations.DestinationAutocompletePresenter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_place_autocomplete.view.*

class PlaceAutocompleteItemView(val place: Place,
                                val presenter: DestinationAutocompletePresenter) : Item() {


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.placeName.text = place.nameUz

        viewHolder.itemView.autocompleteItemparent.setOnClickListener {
            presenter.dispatchItemClick(this)
        }
    }

    override fun getLayout() = R.layout.item_place_autocomplete

}
