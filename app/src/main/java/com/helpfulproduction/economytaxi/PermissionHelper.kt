package com.helpfulproduction.economytaxi

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

    fun requestIfFirstAttempt(fragment: Fragment, permission: String, requestCode: Int): Boolean {
        val context = fragment.context ?: return false

        if (!Preference.isAlreadyRequestedPermission(context, permission)) {
            requestPermission(fragment, permission, requestCode)
            return true
        }
        return false
    }

    fun requestPermission(fragment: Fragment, permission: String, requestCode: Int) {
        val context = fragment.context ?: return

        fragment.requestPermissions(arrayOf(permission), requestCode)
        Preference.setRequestedPermission(context, permission)
    }
}