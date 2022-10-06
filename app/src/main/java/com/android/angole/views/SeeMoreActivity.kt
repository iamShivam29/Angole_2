package com.android.angole.views

import android.content.Intent
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.angole.R
import com.android.angole.adapters.MainRecyclerAdapter
import com.android.angole.adapters.MoreItemsRecyclerAdapter
import com.android.angole.config.AuthConfig
import com.android.angole.databinding.ActivitySeeMoreBinding
import com.android.angole.models.CategoryData
import com.android.angole.models.CategoryItems
import com.android.angole.utils.Constants
import com.android.angole.utils.MarginItemDecoration
import com.android.angole.viewmodels.StreamViewModel
import com.android.angole.viewmodels.UserViewModel


class SeeMoreActivity : AppCompatActivity(), MoreItemsRecyclerAdapter.OnClickEvent {
    private var binding: ActivitySeeMoreBinding? = null
    private var totalPage = 10
    private var pageNo = 1
    private var isLoading = false
    private var streamViewModel: StreamViewModel? = null
    private val list = mutableListOf<CategoryItems>()
    private var adapter: MoreItemsRecyclerAdapter? = null
    private var categoryId: Int? = null
    private var categoryName = ""
    private var from: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySeeMoreBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initView()
    }

    private fun initView(){
        streamViewModel = ViewModelProvider(this)[StreamViewModel::class.java]
        categoryId = intent.getStringExtra("categoryId")?.toInt()
        categoryName = intent.getStringExtra("categoryName")!!
        from = intent.getStringExtra("from")

        binding?.tvCategoryName?.text = categoryName


//        val items = listOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
//        list.addAll(items)
        adapter = MoreItemsRecyclerAdapter(this, list,this)
        val itemDecoration = MarginItemDecoration(16, 3, GridLayoutManager.VERTICAL)

        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.orientation = GridLayoutManager.VERTICAL

        binding?.rvSeeMore?.addItemDecoration(itemDecoration)
        binding?.rvSeeMore?.layoutManager = layoutManager
        binding?.rvSeeMore?.adapter = adapter

        when (from) {
            Constants.FROM_SERIES -> {
                loadWebShows()
            }
            Constants.FROM_LIVETV -> {
                // load live Tv
            }
            else -> {
                loadMoviesData()
            }
        }

        applyPagination()
//        binding?.rvSeeMore?.addOnScrollListener(recyclerViewScrollListener(layoutManager))

        binding?.ibBack?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun applyPagination(){
        binding?.scrollView?.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight){
                if(pageNo < totalPage && !isLoading) {
                    binding?.loadMoreProgress?.visibility = View.VISIBLE
                    when (from) {
                        Constants.FROM_SERIES -> {
                            loadWebShows()
                        }
                        Constants.FROM_LIVETV -> {
                            // load live Tv
                        }
                        else -> {
                            loadMoviesData()
                        }
                    }
                    isLoading = true
//                    pageNo++
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

//    private fun loadData(){
//        val handler = Handler(Looper.getMainLooper())
//        handler.postDelayed({
//            val items = listOf("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
//            val position = items.size
//            list.addAll(items)
//
//            binding?.loadMoreProgress?.visibility = View.GONE
////            adapter?.notifyItemInserted(position)
//            adapter?.notifyDataSetChanged()
//        }, 3000)
//    }
//

    private fun loadMoviesData(){
        val authConfig = AuthConfig(this)
        val authToken = "Bearer " + authConfig.getToken()

        streamViewModel?.getAllMovies(authToken, categoryId!!, pageNo)
        streamViewModel?.allMoviesData?.observe(this){
            it?.let {
                if (it.data != null){
                    val subCode = it.data.subCode
                    if (subCode == 200){
                        val items = it.data.items?.data
                        items?.let { itemList ->
                            val position = list.size
                            list.addAll(itemList)
                            adapter?.notifyItemInserted(position)
                            pageNo ++
                            isLoading = false
                            totalPage = it.data.items.totalPage!!

                            binding?.mainContent?.visibility = View.VISIBLE
                            binding?.loadMoreProgress?.visibility = View.GONE
                        }
                    }else {
                        if (pageNo == 1) {
                            binding?.mainContent?.visibility = View.GONE
                            binding?.lvInfo?.visibility = View.VISIBLE
                            binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                            binding?.tvInfo?.text = "Something went wrong"
                        }else{
                            Toast.makeText(this, "Loading Error", Toast.LENGTH_SHORT).show()
                            binding?.lvInfo?.visibility = View.GONE
                        }
                    }

                }else{
//                    if (!it.message.isNullOrEmpty()) {
//                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
//                    }

                    if (pageNo == 1) {
                        binding?.mainContent?.visibility = View.GONE
                        binding?.lvInfo?.visibility = View.VISIBLE
                        binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                        binding?.tvInfo?.text = "Something went wrong"
                    }else{
                        Toast.makeText(this, "Loading Error", Toast.LENGTH_SHORT).show()
                        binding?.lvInfo?.visibility = View.GONE
                    }
                }

                if (pageNo <= 2) {
                    binding?.shimmerContainer?.visibility = View.GONE
                    binding?.shimmerContainer?.stopShimmer()
                    binding?.mainContent?.visibility = View.VISIBLE
                }

                streamViewModel?.allMoviesData?.removeObservers(this)
                streamViewModel?.allMoviesData?.value = null
            }
        }
    }

    private fun loadWebShows(){
        val authConfig = AuthConfig(this)
        val authToken = "Bearer " + authConfig.getToken()

        streamViewModel?.getAllSeries(authToken, categoryId!!, pageNo)
        streamViewModel?.allSeriesData?.observe(this){
            it?.let {
                if (it.data != null){
                    val subCode = it.data.subCode
                    if (subCode == 200){
                        val items = it.data.items?.data
                        items?.let { itemList ->
                            val position = list.size
                            list.addAll(itemList)
                            adapter?.notifyItemInserted(position)
                            pageNo ++

                            totalPage = it.data.items.totalPage!!

                            binding?.mainContent?.visibility = View.VISIBLE
                            binding?.loadMoreProgress?.visibility = View.GONE
                        }
                    }else {
                        if (pageNo == 1) {
                            binding?.mainContent?.visibility = View.GONE
                            binding?.lvInfo?.visibility = View.VISIBLE
                            binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                            binding?.tvInfo?.text = "Something went wrong"
                        }else{
                            Toast.makeText(this, "Loading Error", Toast.LENGTH_SHORT).show()
                            binding?.lvInfo?.visibility = View.GONE
                        }
                    }

                }else{
//                    if (!it.message.isNullOrEmpty()) {
//                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//                    }else{
//                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
//                    }

                    if (pageNo == 1) {
                        binding?.mainContent?.visibility = View.GONE
                        binding?.lvInfo?.visibility = View.VISIBLE
                        binding?.ivInfo?.setImageResource(R.drawable.no_data_found)
                        binding?.tvInfo?.text = "Something went wrong"
                    }else{
                        Toast.makeText(this, "Loading Error", Toast.LENGTH_SHORT).show()
                        binding?.lvInfo?.visibility = View.GONE
                    }
                }

                if (pageNo <= 2) {
                    binding?.shimmerContainer?.visibility = View.GONE
                    binding?.shimmerContainer?.stopShimmer()
                    binding?.mainContent?.visibility = View.VISIBLE
                }
                isLoading = false

                streamViewModel?.allSeriesData?.removeObservers(this)
                streamViewModel?.allSeriesData?.value = null
            }
        }
    }

    override fun onItemClick(categoryItems: CategoryItems) {
        if (categoryItems.type == "SHOWS"){
            val intent = Intent(this, SeriesVideoSelectedActivity::class.java)
            intent.putExtra("id", categoryItems.id)
            intent.putExtra("FromHome", false)
            startActivity(intent)

        }else if (categoryItems.type == "MOVIES"){
            val intent = Intent(this, VideoSelectedActivity::class.java)
            intent.putExtra("id", categoryItems.id)
            intent.putExtra("FromHome", false)
            startActivity(intent)
        }else if (from == Constants.FROM_LIVETV){
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("videoUri", categoryItems.playableUrl)
            intent.putExtra("videoName", categoryItems.name)
            startActivity(intent)
        }
    }
}