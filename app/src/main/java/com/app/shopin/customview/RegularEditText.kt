package com.app.shopin.customview

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.EditText

class RegularEditText : EditText {
    constructor(context: Context) : super(context) {
        applyFont(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        applyFont(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        applyFont(context)
    }


    private fun applyFont(context: Context) {

        try {
            val typeface: Typeface = Typeface.createFromAsset(context.assets, "font_regular.ttf")
            setTypeface(typeface)
        } catch (e: Exception) {
        }

    }
}