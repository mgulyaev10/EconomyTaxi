package com.helpfulproduction.economytaxi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.helpfulproduction.economytaxi.R
import com.helpfulproduction.economytaxi.interfaces.RecentsClickListener
import com.helpfulproduction.economytaxi.ui.holders.RecentPlaceHolder
import com.helpfulproduction.economytaxi.utils.android.Preference

class RecentPlacesAdapter(
    context: Context?,
    private val clickListener: RecentsClickListener?
): RecyclerView.Adapter<RecentPlaceHolder>() {

    private var places: List<String> = if (context == null) {
        emptyList()
    } else {
        Preference.getRecentPlaces(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentPlaceHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_place_holder, parent, false)
        return RecentPlaceHolder(view)
    }

    override fun onBindViewHolder(holder: RecentPlaceHolder, position: Int) {
        if (getItemViewType(position) == RECENT_PLACES_PLACE_VIEW_TYPE) {
            holder.onBind(places[position])
            holder.itemView.setOnClickListener {
                clickListener?.onRecentClick()
            }
        } else {
            holder.itemView.setOnClickListener {
                clickListener?.onDestinationSearchClick()
            }
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