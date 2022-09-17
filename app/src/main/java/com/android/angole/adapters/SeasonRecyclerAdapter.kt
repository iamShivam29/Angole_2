package com.android.angole.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.angole.R
import com.android.angole.databinding.SeasonItemViewBinding
import com.android.angole.models.Episodes
import com.android.angole.models.SeriesInfo

class SeasonRecyclerAdapter(val context: Context, private val seasonList: List<SeriesInfo>, val onClickEvent: OnClickEvent): RecyclerView.Adapter<SeasonRecyclerAdapter.SeasonViewHolder>() {
    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = SeasonItemViewBinding.inflate(inflater)
        return SeasonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeasonViewHolder, position: Int) {
        if (position == selectedPosition){
            holder.binding.tvSeason.setTextColor(ContextCompat.getColor(context, R.color.white))
//            holder.binding.tvSeason.textSize = 16f
        }else{
            holder.binding.tvSeason.setTextColor(ContextCompat.getColor(context, R.color.gray))
        }
        holder.binding.tvSeason.text = seasonList[position].season_element?.name
    }

    override fun getItemCount(): Int {
        return seasonList.size
    }

    inner class SeasonViewHolder(val binding: SeasonItemViewBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.tvSeason.setOnClickListener {
                val episodesList = seasonList[absoluteAdapterPosition].episodes
                onClickEvent.onItemClicked(episodesList)

                val oldPosition = selectedPosition
                selectedPosition = absoluteAdapterPosition
                notifyItemChanged(oldPosition)
                notifyItemChanged(absoluteAdapterPosition)
            }
        }
    }

    interface OnClickEvent{
        fun onItemClicked(episodesList: List<Episodes>?)
    }
}