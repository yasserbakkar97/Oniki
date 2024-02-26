package com.example.oniki

import android.view.View
import android.view.animation.AnimationUtils
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

fun View.slideUp(animTim: Long , startOffset: Long){
    val slidUp = AnimationUtils.loadAnimation(context,R.anim.push).apply {
        duration = animTim
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
    }
    startAnimation(slidUp)
}
