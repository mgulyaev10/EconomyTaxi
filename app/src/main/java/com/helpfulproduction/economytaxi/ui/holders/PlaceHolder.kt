package com.helpfulproduction.economytaxi.ui.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.helpfulproduction.economytaxi.R
import com.helpfulproduction.economytaxi.interfaces.SearchPlaceClickListener
import com.helpfulproduction.economytaxi.models.Place

class PlaceHolder(view: View,
                  clickListener: SearchPlaceClickListener
): RecyclerView.ViewHolder(view) {
    private val address: TextView = view.findViewById(R.id.address)
    private val city: TextView = view.findViewById(R.id.city)

    init {
        view.setOnClickListener {
            clickListener.onPlaceClick()
        }
    }

    fun onBind(place: Place) {
        address.text = place.address
        city.text = place.address
    }

}