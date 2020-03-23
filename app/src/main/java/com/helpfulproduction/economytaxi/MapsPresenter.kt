package com.helpfulproduction.economytaxi

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import java.util.*

class MapsPresenter(
    private val view: MapsContract.View<MapsContract.Presenter>
): MapsContract.Presenter {

    private var map: GoogleMap? = null
    private var currentLatLng: LatLng? = null

    private val onMapReadyCallback =
        OnMapReadyCallback { preparedMap ->
            map = preparedMap?.apply {
                uiSettings.isMyLocationButtonEnabled = false
                setOnCameraMoveStartedListener {
                    view.onCameraMoveStarted()
                }
                setOnCameraIdleListener {
                    resolveAddress()
                    saveCurrentLatLng()
                }
            }
            setPosition()
            setMyLocationEnabledOrNot()
        }

    override fun onCreateMapView(mapView: MapView?) {
        try {
            MapsInitializer.initialize(view.activity())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mapView?.getMapAsync(onMapReadyCallback)
        PermissionHelper.requestPermission(view.fragment(), Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE)
    }

    override fun onResume() {
        if (!PermissionHelper.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, view.activity())) {
            view.showPermissionExplanation()
        } else {
            view.hidePermissionExplanation()
        }
        setPosition()
    }

    override fun onSaveInstanceState(bundle: Bundle) {
        map?.let { map ->
            val target = map.cameraPosition.target
            bundle.putDouble(KEY_LATITUDE, target.latitude)
            bundle.putDouble(KEY_LONGITUDE, target.longitude)
        }
    }

    override fun onRestoreInstanceState(bundle: Bundle?) {
        bundle?.let { savedInstanceState ->
            val lat = bundle.getDouble(KEY_LATITUDE)
            val lng = bundle.getDouble(KEY_LONGITUDE)
            if (lat != 0.0 && lng != 0.0) {
                currentLatLng = LatLng(lat, lng)
            }
        }
    }

    override fun onMyLocationClick() {
        if (PermissionHelper.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, view.activity())) {
            setCurrentPosition()
        } else {
            view.highlightPermissionsWindow()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.isEmpty()) {
            return
        }
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setCurrentPosition()
                view.hidePermissionExplanation()
            } else {
                permissions.forEach {
                    PermissionHelper.rememberDeniedPermission(view.activity(), it)
                }
                setDefaultPosition()
                view.showPermissionExplanation()
            }
        }
    }

    override fun onPermissionWindowClick() {
        PermissionHelper.tryRequestPermission(view.fragment(), Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE)
    }

    private fun resolveAddress() {
        try {
            val geocoder = Geocoder(view.activity(), Locale.getDefault())
            val location = map?.cameraPosition?.target ?: LocationHelper.getDefaultLocation()
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses.size < 0) {
                return
            }
            val address = addresses[0]
            val thoroughfare = address.thoroughfare
            if (thoroughfare == null || thoroughfare == UNNAMED_ROAD) {
                view.onUnknownAddress()
            } else {
                val subThoroughfare = address.subThoroughfare
                if (subThoroughfare == null) {
                    view.onAddressResolved(thoroughfare)
                } else {
                    view.onAddressResolved("$thoroughfare, $subThoroughfare")
                }
            }
        } catch (e: Exception) {
            view.onUnknownAddress()
        }
    }

    private fun setPosition() {
        currentLatLng?.let { latLng ->
            setPosition(latLng)
            return
        }
        if (PermissionHelper.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, view.activity())) {
            setCurrentPosition()
        } else {
            setDefaultPosition()
        }
    }

    private fun setCurrentPosition() {
        map?.toMyLocation(view.activity())
    }

    private fun setDefaultPosition() {
        map?.toDefaultLocation()
    }

    private fun setMyLocationEnabledOrNot() {
        map?.isMyLocationEnabled = PermissionHelper.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, view.activity())
    }

    private fun setPosition(latLng: LatLng) {
        map?.toPosition(latLng)
    }

    private fun saveCurrentLatLng() {
        currentLatLng = map?.cameraPosition?.target
    }

    private companion object {
        private val TAG = MapsPresenter::class.java.simpleName

        private const val LOCATION_REQUEST_CODE = 2142
        private const val UNNAMED_ROAD = "Unnamed Road"

        private const val KEY_LATITUDE = "latitude_key"
        private const val KEY_LONGITUDE = "longitude_key"
    }

}