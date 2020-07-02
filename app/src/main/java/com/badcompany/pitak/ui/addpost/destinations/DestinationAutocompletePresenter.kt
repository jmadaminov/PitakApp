package com.badcompany.pitak.ui.addpost.destinations

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.badcompany.core.ResultWrapper
import com.badcompany.pitak.ui.interfaces.IOnPlaceSearchQueryListener
import com.badcompany.pitak.ui.viewgroups.PlaceAutocompleteItemView
import com.otaliastudios.autocomplete.RecyclerViewPresenter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder


class DestinationAutocompletePresenter(val ctx: Context, val viewModel: DestinationsViewModel,
                                       private val onQueryListener: IOnPlaceSearchQueryListener,
                                       val isFrom: Boolean = true) :
    RecyclerViewPresenter<PlaceAutocompleteItemView>(ctx) {

    override fun instantiateAdapter(): RecyclerView.Adapter<*> {
        return GroupAdapter<GroupieViewHolder>()
    }

    fun getAdr(): GroupAdapter<GroupieViewHolder>? {
        return recyclerView!!.adapter as GroupAdapter<GroupieViewHolder>
    }

    override fun onQuery(query: CharSequence?) {

//        var selectedFromFeed = false
//        for (i in 0 until recyclerView!!.adapter!!.itemCount) {
//            if () {
//                selectedFromFeed = true
//            }
//        }

        var isFromFeed = false


        if (viewModel.fromPlacesResponse.value != null && viewModel.fromPlacesResponse.value is ResultWrapper.Success && (viewModel.fromPlacesResponse.value as ResultWrapper.Success).value.isNotEmpty()) {
            isFromFeed =
                (viewModel.fromPlacesResponse.value as ResultWrapper.Success).value[0].nameUz!! == query.toString()
        }

        onQueryListener.onQuery(query, isFrom, isFromFeed)
        Log.wtf("WTF ", "onQuery: .")
    }

    fun dispatchItemClick(itemView: PlaceAutocompleteItemView) {
        dispatchClick(itemView)
    }

    override fun getPopupDimensions(): PopupDimensions {
        val dims = PopupDimensions()
        dims.width = ViewGroup.LayoutParams.WRAP_CONTENT
        dims.height = ViewGroup.LayoutParams.WRAP_CONTENT
        return dims
    }

    override fun getView(): ViewGroup {
        return super.getView()
    }

    override fun onViewShown() {
        super.onViewShown()
    }
}