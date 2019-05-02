package com.windyroad.nghia.common.view

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Spacing For RecyclerView Item
 * USING: mRecyclerView_Image.addItemDecoration(new DecorationGridSpacingItem(getResources().getDimensionPixelSize(R.dimen.grid_spacing), true))
 */
class GridItemSpacing(private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getLayoutManager() is GridLayoutManager) {
            val layoutManager = parent.getLayoutManager() as GridLayoutManager
            val spanCount = layoutManager.getSpanCount()
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount

                if (position < spanCount) {
                    outRect.top = spacing
                }
                outRect.bottom = spacing
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                if (position >= spanCount) {
                    outRect.top = spacing
                }
            }
        }
    }
}