package com.android.angole.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.angole.databinding.ThumbnailGridItemViewBinding
import com.android.angole.databinding.ThumbnailItemViewBinding
import com.android.angole.models.CategoryItems
import com.bumptech.glide.Glide

class MoreItemsRecyclerAdapter(val context: Context, private val itemsList: List<CategoryItems?>, val onclickEvent: OnClickEvent): RecyclerView.Adapter<MoreItemsRecyclerAdapter.MoreItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreItemViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ThumbnailGridItemViewBinding.inflate(inflater, parent, false)
        return MoreItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoreItemViewHolder, position: Int) {
        val url = itemsList[position]?.cover
        if (!(context as Activity).isFinishing){
            Glide.with(context)
                .load(url)
                .into(holder.binding.ivThumbnail)
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    inner class MoreItemViewHolder(val binding: ThumbnailGridItemViewBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.thumbnailRoot.setOnClickListener {
                val categoryItems = itemsList[absoluteAdapterPosition]

                if (categoryItems == null){
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }else {
                    onclickEvent.onItemClick(categoryItems)
                }
            }
        }
    }

    interface OnClickEvent{
        fun onItemClick(categoryItems: CategoryItems)
    }
}