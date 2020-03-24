package com.helpfulproduction.economytaxi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.helpfulproduction.economytaxi.R
import com.helpfulproduction.economytaxi.interfaces.SearchPlaceClickListener
import com.helpfulproduction.economytaxi.models.Place
import com.helpfulproduction.economytaxi.ui.holders.PlaceHolder

class PlacesSearchAdapter(
    private val placeClickListener: SearchPlaceClickListener
): RecyclerView.Adapter<PlaceHolder>() {

    var places: List<Place> = listOf(
        Place(LatLng(60.0, 60.0), "Невский 28"),
        Place(LatLng(60.0, 60.0), "Невский 28"),
        Place(LatLng(60.0, 60.0), "Невский 28"),
        Place(LatLng(60.0, 60.0), "Невский 28")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_place_holder, parent, false)
        return PlaceHolder(view, placeClickListener)
    }

    override fun onBindViewHolder(holder: PlaceHolder, position: Int) {
        holder.onBind(places[position])
    }

    override fun getItemCount(): Int = places.size

}