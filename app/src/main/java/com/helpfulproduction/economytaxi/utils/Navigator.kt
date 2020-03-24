package com.helpfulproduction.economytaxi.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object Navigator {
    fun openSystemSettings(context: Context?) {
        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", context?.packageName, null)
        context?.startActivity(intent)
    }
}