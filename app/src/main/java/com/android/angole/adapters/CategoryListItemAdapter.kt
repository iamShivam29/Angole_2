package com.android.angole.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.angole.R
import com.android.angole.databinding.ThumbnailItemViewBinding
import com.android.angole.models.CategoryItems
import com.bumptech.glide.Glide

class CategoryListItemAdapter(val context: Context, private val itemList: List<CategoryItems>, val onMainClick: OnMainClick): RecyclerView.Adapter<CategoryListItemAdapter.CategoryItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ThumbnailItemViewBinding.inflate(inflater)
        return CategoryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int) {
        val imageUrl = itemList[position].cover
        if (imageUrl.isNotEmpty()) {
            if (!(context as Activity).isFinishing) {
                Glide.with(context)
                    .load(itemList[position].cover)
                    .into(holder.binding.ivThumbnail)
            }
        }else{
            if (!(context as Activity).isFinishing) {
                Glide.with(context)
                    .load(R.drawable.ic_image_placeholder)
                    .into(holder.binding.ivThumbnail)
            }
        }

    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    inner class CategoryItemViewHolder(val binding: ThumbnailItemViewBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.thumbnailRoot.setOnClickListener {
                val type = itemList[absoluteAdapterPosition].type
                val id = itemList[absoluteAdapterPosition].id
                onMainClick.onItemClick(type, id)
            }
        }
    }

    interface OnMainClick{
        fun onItemClick(type: String, id: Int)
    }
}
