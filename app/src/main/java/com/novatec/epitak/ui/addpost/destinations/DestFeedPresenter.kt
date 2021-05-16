package com.novatec.epitak.ui.addpost.destinations

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.novatec.epitak.ui.interfaces.IOnPlaceSearchQueryListener
import com.novatec.epitak.ui.viewgroups.PlaceFeedItemView
import com.otaliastudios.autocomplete.RecyclerViewPresenter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder


class DestFeedPresenter(val ctx: Context, val inputEditText: EditText,
                        private val onQueryListener: IOnPlaceSearchQueryListener,
                        val isFrom: Boolean = true) :
    RecyclerViewPresenter<PlaceFeedItemView>(ctx) {

    override fun instantiateAdapter(): RecyclerView.Adapter<*> {
        return GroupAdapter<GroupieViewHolder>()
    }

    fun getAdr(): GroupAdapter<GroupieViewHolder>? {
        return if (recyclerView == null) null else recyclerView!!.adapter as GroupAdapter<GroupieViewHolder>
    }

    fun disposeAdr() {
        recyclerView?.adapter = null
    }

    override fun onQuery(query: CharSequence?) {
        onQueryListener.onQuery(query, isFrom, !inputEditText.isFocused)
        Log.wtf("WTF ", "onQuery: .")
    }

    fun dispatchItemClick(itemView: PlaceFeedItemView) {
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