package com.example.weatherforecastapplication.gradient_textview

import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.weatherforecastapplication.R

class MyGradientTextView: androidx.appcompat.widget.AppCompatTextView {

    var primaryColor:Int=0
    var scondaryColor:Int=0

    constructor(context:Context):super(context)
    constructor(context: Context,attrs:AttributeSet):super(context,attrs)
    constructor(context: Context,attrs: AttributeSet,defstyleAttr:Int):super(context,attrs,defstyleAttr)

    fun setColor(primaryColor:Int,scondaryColor:Int){
        this.primaryColor=primaryColor
        this.scondaryColor=scondaryColor
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if(changed){
            paint.shader=LinearGradient(0f,0f,width.toFloat(),height.toFloat(),
                ContextCompat.getColor(context, R.color.lightblue),
                ContextCompat.getColor(context, R.color.blue),
            Shader.TileMode.CLAMP)
        }

    }
}