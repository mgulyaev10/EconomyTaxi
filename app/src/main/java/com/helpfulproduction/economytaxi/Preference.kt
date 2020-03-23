package com.helpfulproduction.economytaxi

import android.content.Context

object Preference {
    private const val RECENTS_PREF_KEY = "recents_pref_key"
    private const val RECENTS_KEY = "recents_key"

    fun getRecentPlaces(context: Context): List<String> {
        return context.getSharedPreferences(RECENTS_PREF_KEY, Context.MODE_PRIVATE)
            .getStringSet(RECENTS_KEY, emptySet())
            ?.toList() ?: emptyList()
    }

}