package com.example.admin.smart_translator.LayoutManagers

import android.content.Context
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class CenterZoomLayoutManager : androidx.recyclerview.widget.LinearLayoutManager {

    private val mShrinkAmount = 0.8f
    private val mShrinkDistance = 1.5f
    private val lan: TextView

    constructor(context: Context) : super(context) {}

    constructor(context: Context, orientation: Int, reverseLayout: Boolean, lan: TextView) : super(context, orientation, reverseLayout) {
        this.lan = lan
    }

    override fun scrollVerticallyBy(dy: Int, recycler: androidx.recyclerview.widget.RecyclerView.Recycler?, state: androidx.recyclerview.widget.RecyclerView.State?): Int {
        val orientation = orientation
        if (orientation == androidx.recyclerview.widget.LinearLayoutManager.VERTICAL) {
            val scrolled = super.scrollVerticallyBy(dy, recycler, state)
            val midpoint = height / 2f
            val d0 = 0f
            val d1 = mShrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - mShrinkAmount
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                val childMidpoint = (getDecoratedBottom(child!!) + getDecoratedTop(child)) / 2f
                val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                child.scaleX = scale
                child.scaleY = scale

                if (i % childCount == childCount / 2) {
                    val relativeLayout = child as RelativeLayout?
                    val imageView = relativeLayout!!.getChildAt(0) as ImageView
                    lan.setText(imageView.contentDescription.toString().toUpperCase())
                }

            }
            return scrolled
        } else {
            return 0
        }
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: androidx.recyclerview.widget.RecyclerView.Recycler?, state: androidx.recyclerview.widget.RecyclerView.State?): Int {
        val orientation = orientation
        if (orientation == androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL) {
            val scrolled = super.scrollHorizontallyBy(dx, recycler, state)

            val midpoint = width / 2f
            val d0 = 0f
            val d1 = mShrinkDistance * midpoint
            val s0 = 1f
            val s1 = 1f - mShrinkAmount
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                val childMidpoint = (getDecoratedRight(child!!) + getDecoratedLeft(child)) / 2f
                val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
                val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
                child.scaleX = scale
                child.scaleY = scale
            }
            return scrolled
        } else {
            return 0
        }

    }
}