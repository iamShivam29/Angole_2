package com.android.angole.utils

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationScrollListener(val layoutManager: GridLayoutManager): RecyclerView.OnScrollListener() {
    val PAGE_START = 1
//    private var layoutManager: GridLayoutManager? = null
//
//    /**
//     * Set scrolling threshold here (for now i'm assuming 10 item in one page)
//     */
//    private val PAGE_SIZE = 12
//
//    /**
//     * Supporting only LinearLayoutManager for now.
//     */
//    fun PaginationListener(layoutManager: LinearLayoutManager) {
//        this.layoutManager = layoutManager
//    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount: Int = layoutManager.childCount
        val totalItemCount: Int = layoutManager.itemCount
        val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
}