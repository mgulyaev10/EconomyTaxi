package com.helpfulproduction.economytaxi.utils.android.anim

import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import com.helpfulproduction.economytaxi.utils.extensions.setVisible

object AnimationHelper {

    fun showOneViewFromAnotherVertically(view: View, from: View, offsetY: Float, duration: Long): Animation {
        val listener = object :
            SimpleAnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                view.setVisible()
            }
        }
        val animation = TranslateAnimation(0f, 0f, from.y - view.y + offsetY, 0f).apply {
            this.setAnimationListener(listener)
            this.duration = duration
        }
        view.startAnimation(animation)
        return animation
    }

}