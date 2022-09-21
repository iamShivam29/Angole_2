package com.android.angole.utils

import android.R.attr.spacing
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


//class ItemOffsetDecoration constructor(private val itemOffset: Int): RecyclerView.ItemDecoration() {
//    constructor(context: Context, @DimenRes itemOffsetId: Int): this(itemOffsetId) {
//        context.resources.getDimensionPixelSize(itemOffsetId)
//    }
//
//    override fun getItemOffsets(outRect: Rect,view: View, parent: RecyclerView, state: RecyclerView.State) {
//        super.getItemOffsets(outRect, view, parent, state)
//        outRect.set(itemOffset, itemOffset, itemOffset, itemOffset)
//    }
//}

class MarginItemDecoration(
    private val spacing: Int,
    private val spanCount: Int = 1,
    private val orientation: Int = GridLayoutManager.VERTICAL,
    private val includeEdge: Boolean =  true
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//        with(outRect) {
//            if (orientation == GridLayoutManager.VERTICAL) {
//                if (parent.getChildAdapterPosition(view) < spanCount) {
//                    top = spaceSize
//                }
//                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
//                    left = spaceSize
//                }
//            } else {
//                if (parent.getChildAdapterPosition(view) < spanCount) {
//                    left = spaceSize
//                }
//                if (parent.getChildAdapterPosition(view) % spanCount == 0) {
//                    top = spaceSize
//                }
//            }
//
//            right = spaceSize
//            bottom = spaceSize
//        }

        val position: Int = parent.getChildAdapterPosition(view) // item position

        val column = position % spanCount // item column


        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right =
                (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom
        } else {
            outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right =
                spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }
}