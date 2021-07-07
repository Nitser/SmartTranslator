package com.example.admin.smart_translator.layout_managers

import android.content.Context
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.smart_translator.R
import java.util.Locale
import kotlin.math.abs
import kotlin.math.min

class CenterZoomFlagLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean, private val flagNameTextView: TextView)
    : LinearLayoutManager(context, orientation, reverseLayout) {

    private val shrinkAmount = 0.8f
    private val shrinkDistance = 1.5f
    private val startShrinkDistance = 0f
    private val largeShrinkAmount = 1f
    private val smallShrinkAmount = 1f - shrinkAmount

    private val flagItemId = R.id.layout_scrollable_flag_icon
    lateinit var currentFlagIndex: String

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return if (orientation == VERTICAL) {
            val midpoint = height / 2f
            val middleShrinkDistance = shrinkDistance * midpoint
            for (childIndex in 0 until childCount) {
                val child = getChildAt(childIndex)
                val childMidpoint = (getDecoratedBottom(child!!) + getDecoratedTop(child)) / 2f
                val currentShrinkDistance = min(middleShrinkDistance, abs(midpoint - childMidpoint))
                val scale = largeShrinkAmount + (smallShrinkAmount - largeShrinkAmount) *
                        (currentShrinkDistance - startShrinkDistance) / (middleShrinkDistance - startShrinkDistance)
                child.scaleX = scale
                child.scaleY = scale

                setCenterLanguageName(childIndex, child as RelativeLayout)
            }
            super.scrollVerticallyBy(dy, recycler, state)
        } else {
            0
        }
    }

    override fun scrollHorizontallyBy(dx: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        return if (orientation == HORIZONTAL) {
            val midpoint = width / 2f
            val middleShrinkDistance = shrinkDistance * midpoint
            for (childIndex in 0 until childCount) {
                val child = getChildAt(childIndex)
                val childMidpoint = (getDecoratedRight(child!!) + getDecoratedLeft(child)) / 2f
                val currentShrinkDistance = min(middleShrinkDistance, abs(midpoint - childMidpoint))
                val scale = largeShrinkAmount + (smallShrinkAmount - largeShrinkAmount) *
                        (currentShrinkDistance - startShrinkDistance) / (middleShrinkDistance - startShrinkDistance)
                child.scaleX = scale
                child.scaleY = scale
            }
            super.scrollHorizontallyBy(dx, recycler, state)
        } else {
            0
        }

    }

    private fun setCenterLanguageName(index: Int, relativeLayout: RelativeLayout) {
        if (index % childCount == childCount / 2) {
            val imageView = relativeLayout.findViewById<ImageView>(flagItemId)
            val countryShortName = imageView.contentDescription.toString()
            flagNameTextView.text = countryShortName.toUpperCase(Locale.UK)
            currentFlagIndex = countryShortName.toLowerCase(Locale.UK)
        }
    }
}
