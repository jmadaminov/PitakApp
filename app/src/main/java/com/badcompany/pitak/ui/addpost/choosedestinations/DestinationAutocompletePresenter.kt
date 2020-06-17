package com.badcompany.pitak.ui.addpost.choosedestinations

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.badcompany.pitak.ui.`interface`.IOnPlaceSearchQueryListener
import com.badcompany.pitak.ui.viewgroups.PlaceAutocompleteItemView
import com.otaliastudios.autocomplete.RecyclerViewPresenter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder

class DestinationAutocompletePresenter(ctx: Context,
                                       private val onQueryListener: IOnPlaceSearchQueryListener) :
    RecyclerViewPresenter<PlaceAutocompleteItemView>(ctx) {


    override fun instantiateAdapter(): RecyclerView.Adapter<*> {
        return GroupAdapter<GroupieViewHolder>()
    }

    override fun onQuery(query: CharSequence?) {
        onQueryListener.onQuery(query)
        Log.wtf("WTF ", "onQuery: .")
    }

}