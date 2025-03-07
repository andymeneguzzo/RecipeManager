package com.andy.recipemanager.views

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

// NOT USED FOR RECIPE, MIGHT BE USED FOR OTHER THINGS

class SquareCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        // width on both params to render a square
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}