package com.helpfulproduction.economytaxi

import android.Manifest
import android.content.Context
import android.location.LocationManager
import com.google.android.gms.maps.model.LatLng

object LocationHelper {

    fun getLocation(context: Context?): LatLng {
        if (context == null || !PermissionHelper.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, context)) {
            return getDefaultLocation()
        }
        (context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager)?.apply {
            getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                return LatLng(it.latitude, it.longitude)
            }
        }
        return getDefaultLocation()
    }

    fun getDefaultLocation(): LatLng = LatLng(55.75, 37.61)

}