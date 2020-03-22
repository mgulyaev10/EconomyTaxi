package com.helpfulproduction.economytaxi

import android.app.Activity
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.MapView

interface MapsContract {

    interface Presenter {
        fun onCreateView(mapView: MapView?)
        fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        )
        fun onZoomInClick()
        fun onZoomOutClick()
        fun onMyLocationClick()
    }

    interface View<Presenter> {
        fun onCameraMoveStarted()
        fun onAddressResolved(address: String)
        fun onUnknownAddress()

        fun activity(): Activity?
        fun fragment(): Fragment
    }

}