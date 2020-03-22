package com.helpfulproduction.economytaxi

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecentPlaceHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val icon = itemView.findViewById<ImageView>(R.id.icon)
    private val address = itemView.findViewById<TextView>(R.id.address)

    fun onBind(text: String) {
        icon.setGone()
        address.text = text
    }

}