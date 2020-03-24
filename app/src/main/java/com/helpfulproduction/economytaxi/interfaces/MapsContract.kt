package com.helpfulproduction.economytaxi.interfaces

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.MapView
import com.helpfulproduction.economytaxi.interfaces.RecentsClickListener
import com.helpfulproduction.economytaxi.models.Place

interface MapsContract {

    interface Presenter: RecentsClickListener {
        fun onCreateMapView(mapView: MapView?)
        fun onResume()
        fun onSaveInstanceState(bundle: Bundle)
        fun onRestoreInstanceState(bundle: Bundle?)
        fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        )
        fun onMyLocationClick()
        fun onPermissionWindowClick()
    }

    interface View<Presenter> {
        fun onCameraMoveStarted()
        fun onAddressResolved(address: String)
        fun onUnknownAddress()
        fun showPermissionExplanation()
        fun hidePermissionExplanation()
        fun highlightPermissionsWindow()
        fun openSearchScreen(from: Place)

        fun activity(): Activity?
        fun fragment(): Fragment
    }

}