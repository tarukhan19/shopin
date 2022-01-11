package com.app.shopin.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

@SuppressLint("AppCompatCustomView")
public class RegularTextView : TextView {
    constructor(context: Context) : super(context) {

        applyFont(context)
    }


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {

        applyFont(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        applyFont(context)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        applyFont(context)

    }

    private fun applyFont(context: Context) {

        try {
            val typeface: Typeface = Typeface.createFromAsset(context.assets, "font_regular.ttf")
            setTypeface(typeface)
           // setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12F, context.getResources().getDisplayMetrics()))
        } catch (e: Exception) {
        }

    }
}