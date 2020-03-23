package com.helpfulproduction.economytaxi

import android.app.Activity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng

fun GoogleMap?.toMyLocation(activity: Activity?) {
    val myLocation = LocationHelper.getLocation(activity)
    toPosition(myLocation)
}

fun GoogleMap?.toDefaultLocation() {
    val defaultLocation = LocationHelper.getDefaultLocation()
    toPosition(defaultLocation)
}

fun GoogleMap?.toPosition(location: LatLng) {
    this?.moveCamera(CameraUpdateFactory.newLatLng(location))
}