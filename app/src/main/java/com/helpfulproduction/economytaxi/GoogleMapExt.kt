package com.helpfulproduction.economytaxi

import android.app.Activity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap

fun GoogleMap?.zoomIn() {
    this?.cameraPosition?.zoom?.let { currentZoom ->
        animateCamera(CameraUpdateFactory.zoomTo(currentZoom + 1))
    }
}

fun GoogleMap?.zoomOut() {
    this?.cameraPosition?.zoom?.let { currentZoom ->
        animateCamera(CameraUpdateFactory.zoomTo(currentZoom - 1))
    }
}

fun GoogleMap?.toMyLocation(activity: Activity?) {
    val location = LocationHelper.getLocation(activity)
    this?.moveCamera(CameraUpdateFactory.newLatLng(location))
}

fun GoogleMap?.toDefaultLocation() {
    val location = LocationHelper.getDefaultLocation()
    this?.moveCamera(CameraUpdateFactory.newLatLng(location))
}