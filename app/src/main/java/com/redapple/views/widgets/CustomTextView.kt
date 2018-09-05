package com.redapple.views.widgets


import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.redapple.R

import com.redapple.utils.Utils


class CustomTextView : AppCompatTextView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setCustomFont(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        if (isInEditMode)
            return

        setCustomFont(context, attrs)
    }

    private fun setCustomFont(ctx: Context, attrs: AttributeSet) {
        val a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
        val customFont = a.getString(R.styleable.CustomTextView_typeface)
        typeface = Utils.getTypeFace(ctx, customFont)
        a.recycle()
    }

    companion object {
        private val TAG = "TextView"
    }
}