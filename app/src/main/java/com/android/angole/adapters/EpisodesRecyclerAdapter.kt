package com.android.angole.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.angole.R
import com.android.angole.databinding.EpisodeItemViewBinding
import com.android.angole.models.Episodes
import com.bumptech.glide.Glide

class EpisodesRecyclerAdapter(val context: Context, private val episodesList: List<Episodes>, val onClickEvent: OnClickEvent): RecyclerView.Adapter<EpisodesRecyclerAdapter.MyEpisodesViewHolder>() {
    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyEpisodesViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = EpisodeItemViewBinding.inflate(inflater, parent, false)
        return MyEpisodesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyEpisodesViewHolder, position: Int) {
        if (selectedPosition == position){
            holder.binding.selectedView.visibility = View.VISIBLE
        }else{
            holder.binding.selectedView.visibility = View.INVISIBLE
        }

        holder.binding.tvVideoName.text = "Episode ${episodesList[position].episode_num}"
        holder.binding.tvDescription.text = episodesList[position].info?.plot

        val movieImage = episodesList[position].info?.movie_image
        val coverBig = episodesList[position].info?.cover_big

        if (!coverBig.isNullOrEmpty()){
            if (!(context as Activity).isFinishing) {
                Glide.with(context)
                    .load(coverBig)
                    .into(holder.binding.ivThumbnail)
            }
        }
        else if (!movieImage.isNullOrEmpty()) {
            if (!(context as Activity).isFinishing) {
                Glide.with(context)
                    .load(movieImage)
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
        return episodesList.size
    }

    inner class MyEpisodesViewHolder(val binding: EpisodeItemViewBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.rootLayout.setOnClickListener {
                val oldPosition = selectedPosition
                selectedPosition = absoluteAdapterPosition
                if (oldPosition >= 0){
                    notifyItemChanged(oldPosition)
                }
                notifyItemChanged(absoluteAdapterPosition)

                onClickEvent.onItemClicked(episodesList[absoluteAdapterPosition])
            }
        }
    }

    interface OnClickEvent{
        fun onItemClicked(episode: Episodes)
    }
}