package com.android.angole.adapters

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.angole.R
import com.android.angole.databinding.AvatarItemViewBinding
import com.android.angole.models.AvatarItems
import com.android.angole.utils.Constants
import com.bumptech.glide.Glide

class AvatarRecyclerAdapter(val context: Context, private val avatarList: List<AvatarItems>, val onClickEvent: OnClickEvent): RecyclerView.Adapter<AvatarRecyclerAdapter.MyAvatarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAvatarViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = AvatarItemViewBinding.inflate(inflater, parent, false)
        return MyAvatarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyAvatarViewHolder, position: Int) {
        val url = avatarList[position].url
        if (url?.isEmpty()!!){
            holder.binding.ivProfile.visibility = View.INVISIBLE
            holder.binding.avatarShimmer.visibility = View.VISIBLE
            holder.binding.avatarShimmer.startShimmer()
        }else {
            if (!(context as Activity).isFinishing){
                val fullUrl = Constants.BASE_URL+url
                Glide.with(context)
                    .load(fullUrl)
                    .into(holder.binding.ivProfile)
            }
        }
    }

    override fun getItemCount(): Int {
        return avatarList.size
    }

    inner class MyAvatarViewHolder(val binding: AvatarItemViewBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.ivProfile.setOnClickListener {
                onClickEvent.onItemClicked(avatarList[absoluteAdapterPosition])
            }
        }
    }

    interface OnClickEvent{
        fun onItemClicked(items: AvatarItems)
    }
}