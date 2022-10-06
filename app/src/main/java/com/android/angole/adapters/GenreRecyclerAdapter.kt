package com.android.angole.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.angole.databinding.GenreItemViewBinding

class GenreRecyclerAdapter(val context: Context, private val genreList: List<String>): RecyclerView.Adapter<GenreRecyclerAdapter.MyGenreViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGenreViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = GenreItemViewBinding.inflate(inflater, parent, false)
        return MyGenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyGenreViewHolder, position: Int) {
        holder.binding.tvGenre1.text = genreList[position]
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    inner class MyGenreViewHolder(val binding: GenreItemViewBinding): RecyclerView.ViewHolder(binding.root)
}