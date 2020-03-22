package com.helpfulproduction.economytaxi

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.gms.maps.*
import java.util.*

class MapsPresenter(
    private val view: MapsContract.View<MapsContract.Presenter>
): MapsContract.Presenter {

    private var map: GoogleMap? = null
    private val handler = Handler(Looper.getMainLooper())
    private var loadAddressAttempts = 0

    private val onMapReadyCallback =
        OnMapReadyCallback { preparedMap ->
            map = preparedMap?.apply {
                uiSettings.isMyLocationButtonEnabled = false
                setOnCameraMoveStartedListener {
                    view.onCameraMoveStarted()
                }
                setOnCameraIdleListener {
                    resolveAddress()
                }
            }
            setPosition()
        }

    override fun onCreateView(mapView: MapView?) {
        try {
            MapsInitializer.initialize(view.activity())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mapView?.getMapAsync(onMapReadyCallback)
    }

    override fun onZoomInClick() {
        map?.zoomIn()
    }

    override fun onZoomOutClick() {
        map?.zoomOut()
    }

    override fun onMyLocationClick() {
        if (PermissionHelper.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, view.activity())) {
            setCurrentPosition()
        } else {
            PermissionHelper.requestPermission(view.fragment(), Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setCurrentPosition()
            } else {
                setDefaultPosition()
            }
        }
    }

    private fun resolveAddress() {
        try {
            val geocoder = Geocoder(view.activity(), Locale.getDefault())
            val location = map?.cameraPosition?.target ?: LocationHelper.getDefaultLocation()
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            val address = addresses[0]
            view.onAddressResolved("${address.thoroughfare}, ${address.subThoroughfare}")
        } catch (e: Exception) {
            tryResolveAgain()
        }
    }

    private fun tryResolveAgain() {
        if (loadAddressAttempts < 3) {
            resolveAddress()
            loadAddressAttempts += 1
        } else {
            handler.postDelayed({
                Log.d(TAG, "Reset attempts")
                loadAddressAttempts = 0
            }, RESET_ATTEMPTS_DELAY_MS)
        }
    }

    private fun setPosition() {
        if (PermissionHelper.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, view.activity())) {
            setCurrentPosition()
        } else {
            val isRequested = PermissionHelper.requestIfFirstAttempt(view.fragment(), Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE)
            if (!isRequested) {
                setDefaultPosition()
            }
        }
    }

    private fun setCurrentPosition() {
        map?.isMyLocationEnabled = true
        map?.toMyLocation(view.activity())
    }

    private fun setDefaultPosition() {
        map?.isMyLocationEnabled = false
        map?.toDefaultLocation()
    }

    private companion object {
        private val TAG = MapsPresenter.javaClass.simpleName

        private const val LOCATION_REQUEST_CODE = 2142
        private const val RESET_ATTEMPTS_DELAY_MS = 1000L
    }

}