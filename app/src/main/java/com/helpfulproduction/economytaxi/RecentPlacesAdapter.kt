package com.helpfulproduction.economytaxi

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecentPlacesAdapter(
    context: Context?
): RecyclerView.Adapter<RecentPlaceHolder>() {

    private var places: List<String> = if (context == null) {
        emptyList()
    } else {
        Preference.getRecentPlaces(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentPlaceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_place_holder, parent, false)
        return when (viewType) {
            RECENT_PLACES_DESTINATION_VIEW_TYPE -> RecentPlaceHolder(view)
            RECENT_PLACES_PLACE_VIEW_TYPE -> RecentPlaceHolder(view)
            else -> throw IllegalArgumentException("Unsupported viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecentPlaceHolder, position: Int) {
        if (getItemViewType(position) == RECENT_PLACES_PLACE_VIEW_TYPE) {
            holder.onBind(places[position])
        }
    }

    override fun getItemCount(): Int {
        return 1 + places.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> RECENT_PLACES_DESTINATION_VIEW_TYPE
            else -> RECENT_PLACES_PLACE_VIEW_TYPE
        }
    }

    private companion object {
        private const val RECENT_PLACES_DESTINATION_VIEW_TYPE = 0
        private const val RECENT_PLACES_PLACE_VIEW_TYPE = 1
    }

}