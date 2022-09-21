package com.android.angole.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.angole.databinding.ThumbnailGridItemViewBinding
import com.android.angole.databinding.ThumbnailItemViewBinding

class MoreItemsRecyclerAdapter(val context: Context, private val itemsList: List<String>): RecyclerView.Adapter<MoreItemsRecyclerAdapter.MoreItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreItemViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ThumbnailGridItemViewBinding.inflate(inflater, parent, false)
        return MoreItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoreItemViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    inner class MoreItemViewHolder(val binding: ThumbnailGridItemViewBinding): RecyclerView.ViewHolder(binding.root)
}