package com.android.angole.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.angole.R
import com.android.angole.databinding.AvatarItemViewBinding
import com.bumptech.glide.Glide

class AvatarRecyclerAdapter(val context: Context, private val avatarList: List<String>): RecyclerView.Adapter<AvatarRecyclerAdapter.MyAvatarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAvatarViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = AvatarItemViewBinding.inflate(inflater, parent, false)
        return MyAvatarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyAvatarViewHolder, position: Int) {
        Glide.with(context)
            .load("http://139.59.60.119:3000/v1/static/avatar/avatar1.svg")
            .into(holder.binding.ivProfile)
//        holder.binding.ivProfile.setImageResource(R.drawable.ic_image_placeholder)
    }

    override fun getItemCount(): Int {
        return avatarList.size
    }

    inner class MyAvatarViewHolder(val binding: AvatarItemViewBinding): RecyclerView.ViewHolder(binding.root)
}