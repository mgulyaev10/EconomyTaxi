package com.helpfulproduction.economytaxi

import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation

object AnimationHelper {

    fun showOneViewFromAnotherVertically(view: View, from: View, offsetY: Float, duration: Long): Animation {
        val listener = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                view.setVisible()
            }
            override fun onAnimationEnd(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
        }
        val animation = TranslateAnimation(0f, 0f, from.y - view.y + offsetY, 0f)
        animation.setAnimationListener(listener)
        animation.duration = duration
        view.startAnimation(animation)
        return animation
    }

}