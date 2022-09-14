package com.android.angole.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.angole.databinding.PaginationLoaderItemBinding
import com.android.angole.databinding.ThumbnailItemViewBinding
import com.android.angole.models.HomeData
import com.android.angole.models.HomeItems
import com.bumptech.glide.Glide

class VideoRecyclerAdapter(val context: Context, val streamList: ArrayList<HomeData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var LOADING = 0
    private var ITEM = 1
    private var isLoadingAdded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        if (viewType == ITEM){
            val itemBinding = ThumbnailItemViewBinding.inflate(inflater, parent, false)
            return ThumbnailViewHolder(itemBinding)
        }

        val loadingBinding = PaginationLoaderItemBinding.inflate(inflater, parent, false)
        return LoadingViewHolder(loadingBinding)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == streamList.size-1 && isLoadingAdded) LOADING else ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val streamData = streamList[position]
        when(getItemViewType(position)){
            LOADING -> {
                (holder as LoadingViewHolder).loadingBinding.progressBar.visibility = View.VISIBLE
            }

            ITEM -> {
                Glide.with(context)
                    .load(streamData)
                    .into((holder as ThumbnailViewHolder).itemBinding.ivThumbnail)
            }
        }
    }

    override fun getItemCount(): Int {
        return streamList.size
    }

    fun addLoadingFooter(){
        isLoadingAdded = true
//        add(HomeData(0, "", HomeItems("")))
    }

    fun removeLoadingFooter(){
        isLoadingAdded = false

        val position = streamList.size - 1
        val result = getItem(position)
        if (result != null){
            streamList.remove(result)
            notifyItemRemoved(position)
        }
    }

    private fun add(homeData: HomeData){
        streamList.add(homeData)
        notifyItemInserted(streamList.size - 1)
    }

    private fun addAll(streams: ArrayList<HomeData>){
        for (item in streams){
            add(item)
        }
    }

    private fun getItem(position: Int): HomeData?{
        if (streamList.size - 1 >= position) {
            return streamList[position]
        }
        return null
    }

    inner class ThumbnailViewHolder(val itemBinding: ThumbnailItemViewBinding): RecyclerView.ViewHolder(itemBinding.root){

    }

    inner class LoadingViewHolder(val loadingBinding: PaginationLoaderItemBinding): RecyclerView.ViewHolder(loadingBinding.root){

    }

}