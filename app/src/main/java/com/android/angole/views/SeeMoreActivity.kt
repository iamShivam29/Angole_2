package com.android.angole.views

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.angole.adapters.MoreItemsRecyclerAdapter
import com.android.angole.databinding.ActivitySeeMoreBinding
import com.android.angole.utils.MarginItemDecoration


class SeeMoreActivity : AppCompatActivity() {
    var binding: ActivitySeeMoreBinding? = null
    var isLoading = false
    var isLastPage = false
    private val totalPage = 10
    var pageNo = 1
    val list = mutableListOf<String>()
    var adapter: MoreItemsRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeeMoreBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        val items = listOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
        list.addAll(items)
        adapter = MoreItemsRecyclerAdapter(this, list)
        val itemDecoration = MarginItemDecoration(16, 3, GridLayoutManager.VERTICAL)

        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.orientation = GridLayoutManager.VERTICAL

        binding?.rvSeeMore?.addItemDecoration(itemDecoration)
        binding?.rvSeeMore?.layoutManager = layoutManager
        binding?.rvSeeMore?.adapter = adapter

        applyPagination()
//        binding?.rvSeeMore?.addOnScrollListener(recyclerViewScrollListener(layoutManager))
    }

    private fun applyPagination(){
        binding?.scrollView?.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight){
                if(pageNo < totalPage) {
                    binding?.loadMoreProgress?.visibility = View.VISIBLE
                    loadData()
                    pageNo++
                    Log.i("SeeMore", "Data is loading")
                }
            }
        })
    }

//    private fun recyclerViewScrollListener(layoutManager: GridLayoutManager): RecyclerView.OnScrollListener{
//        return object: RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                val visibleItemCount: Int = layoutManager.childCount
//                val totalItemCount: Int = layoutManager.itemCount
//                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
//                if (!isLoading && !isLastPage) {
//                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
//                        Log.i("See More", "Load more items")
//                        isLoading = true
////                        val scale = resources.displayMetrics.density
////                        val paddingInPx = (62 * scale + 0.5f)
////                        binding?.rvSeeMore?.onScrollStateChanged(RecyclerView.SCROLL_STATE_IDLE)
////                        binding?.rvSeeMore?.setPadding(0,0,0, paddingInPx.toInt())
////                        binding?.rvSeeMore?.clipToPadding = false
////                        binding?.lvLoading?.visibility = View.VISIBLE
//                        val height = recyclerView.height
//                        Log.i("See More", "dy $dy and height $height")
//                        if (dy == height){
//                            binding?.lvLoading?.visibility = View.VISIBLE
//                        }
//                    }
//                }
//            }
//        }
//    }

    private fun loadData(){
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val items = listOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
            val position = items.size
            list.addAll(items)

            binding?.loadMoreProgress?.visibility = View.GONE
//            adapter?.notifyItemInserted(position)
            adapter?.notifyDataSetChanged()
        }, 3000)
    }
}