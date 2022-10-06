package com.android.angole.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.angole.databinding.HomeItemLayoutBinding
import com.android.angole.models.CategoryData
import com.android.angole.models.HomeItems
import com.bumptech.glide.Glide

class MainRecyclerAdapter(val context: Context, private val itemList: List<CategoryData>, private val onMainClick: CategoryListItemAdapter.OnMainClick, val onClickEvent: OnClickEvent): RecyclerView.Adapter<MainRecyclerAdapter.MainItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = HomeItemLayoutBinding.inflate(inflater)
        return MainItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        holder.binding.tvCategoryName.text = itemList[position].categoryName

        val categoryItemList = itemList[position].categoryItems
        val categoryAdapter = CategoryListItemAdapter(context, categoryItemList!!, onMainClick)
        holder.binding.rvCategoryItem.adapter = categoryAdapter
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class MainItemViewHolder(val binding: HomeItemLayoutBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.btnSeeMore.setOnClickListener {
                val categoryData = itemList[absoluteAdapterPosition]
                onClickEvent.onItemClick(categoryData)
            }
        }
    }

    interface OnClickEvent{
        fun onItemClick(categoryData: CategoryData)
    }
}