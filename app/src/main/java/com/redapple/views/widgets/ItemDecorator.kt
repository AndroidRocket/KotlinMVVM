package com.redapple.views.widgets

import android.content.Context
import android.graphics.Rect

import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView


/**
 * Created by Wilson on 11-03-2018.
 */
class ItemOffsetDecoration(private val mItemOffset: Int) : RecyclerView.ItemDecoration() {

    constructor(context: Context, @DimenRes itemOffsetId: Int) : this(context.resources.getDimensionPixelSize(itemOffsetId)) {}

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset)
    }

}