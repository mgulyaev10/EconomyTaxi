package com.helpfulproduction.economytaxi

import android.content.Context

object Preference {
    private const val PERMISSION_PREF_KEY = "permissions_pref"

    fun isAlreadyRequestedPermission(context: Context, permission: String): Boolean {
        return context.getSharedPreferences(PERMISSION_PREF_KEY, Context.MODE_PRIVATE)
            .getBoolean(permission, false)
    }

    fun setRequestedPermission(context: Context, permission: String) {
        context.getSharedPreferences(PERMISSION_PREF_KEY, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(permission, true)
            .apply()
    }

}