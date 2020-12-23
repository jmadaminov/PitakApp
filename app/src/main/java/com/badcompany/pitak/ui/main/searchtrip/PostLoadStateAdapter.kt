package com.badcompany.pitak.ui.main.searchtrip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.badcompany.pitak.R

class PostLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<PostLoadStateAdapter.LoadStateViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.load_state_footer, parent, false)
        return LoadStateViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    inner class LoadStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.findViewById<Button>(R.id.button_retry).setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            itemView.findViewById<ProgressBar>(R.id.progress_bar).isVisible = loadState is LoadState.Loading
            itemView.findViewById<Button>(R.id.button_retry).isVisible = loadState !is LoadState.Loading
            itemView.findViewById<TextView>(R.id.text_view_error).isVisible = loadState !is LoadState.Loading
        }

    }

}