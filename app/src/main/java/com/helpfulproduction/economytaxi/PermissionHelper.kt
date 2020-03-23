package com.helpfulproduction.economytaxi

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

object PermissionHelper {

    fun checkPermission(permission: String, context: Context?): Boolean {
        if (context == null) {
            return false
        }
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun tryRequestPermission(fragment: Fragment, permission: String, requestCode: Int) {
        if (dontAskAgainPermission(fragment.activity, permission)) {
            Navigator.openSystemSettings(fragment.context)
        } else {
            requestPermission(fragment, permission, requestCode)
        }
    }

    fun requestPermission(fragment: Fragment, permission: String, requestCode: Int) {
        fragment.requestPermissions(arrayOf(permission), requestCode)
    }

    fun rememberDeniedPermission(context: Context?, permission: String) {
        if (context == null) {
            return
        }
        Preference.setDeniedPermission(context, permission)
    }

    private fun dontAskAgainPermission(activity: Activity?, permission: String): Boolean {
        if (activity == null) {
            return false
        }
        return !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) && Preference.isAlreadyDeniedPermission(activity, permission)
    }

}