package com.helpfulproduction.economytaxi.ui.fragments

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.MapView
import com.helpfulproduction.economytaxi.*
import com.helpfulproduction.economytaxi.adapters.RecentPlacesAdapter
import com.helpfulproduction.economytaxi.core.MapsPresenter
import com.helpfulproduction.economytaxi.interfaces.MapsContract
import com.helpfulproduction.economytaxi.models.Place
import com.helpfulproduction.economytaxi.utils.extensions.setGone
import com.helpfulproduction.economytaxi.utils.extensions.setVisible
import com.helpfulproduction.economytaxi.utils.android.anim.AnimationHelper

class MapsFragment: Fragment(),
    MapsContract.View<MapsContract.Presenter> {
    private var presenter: MapsContract.Presenter? = null

    private var mapView: MapView? = null

    private var recents: RecyclerView? = null
    private lateinit var addressView: TextView
    private lateinit var permissionTopExplanation: ViewGroup
    private var addressAnimation: Animation? = null

    private lateinit var marker: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maps, container, false)
        presenter = MapsPresenter(this)

        mapView = view.findViewById(R.id.map)
        mapView?.onCreate(savedInstanceState)
        presenter?.onCreateMapView(mapView)

        initViews(view)

        return view
    }

    override fun openSearchScreen(from: Place) {
        fragmentManager?.let { fm ->
            BottomSheetSearchFragment
                .Builder()
                .setPlaceFrom(from)
                .build()
                .show(fm, BottomSheetSearchFragment.TAG)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
        presenter?.onResume()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        presenter?.onRestoreInstanceState(savedInstanceState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        presenter?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCameraMoveStarted() {
        addressView.setGone()
        addressAnimation?.cancel()
        addressAnimation = null
    }

    override fun onUnknownAddress() {
        addressView.setGone()
    }

    override fun onAddressResolved(address: String) {
        addressView.text = address
        showAddressViewAnimated()
    }

    override fun showPermissionExplanation() {
        permissionTopExplanation.setVisible()
    }

    override fun hidePermissionExplanation() {
        permissionTopExplanation.setGone()
    }

    override fun highlightPermissionsWindow() {
        context?.let { ctx ->
            permissionTopExplanation.background = ColorDrawable(ContextCompat.getColor(ctx,
                R.color.gray_zoom_btn_bg
            ))
            permissionTopExplanation.postDelayed({
                permissionTopExplanation.background = ContextCompat.getDrawable(ctx,
                    R.drawable.bg_ripple
                )
            }, 300L)
        }
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter?.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun activity(): Activity? = activity
    override fun fragment(): Fragment = this

    private fun showAddressViewAnimated() {
        addressAnimation =
            AnimationHelper.showOneViewFromAnotherVertically(
                addressView,
                marker,
                -100F,
                300L
            )
    }

    private fun initViews(view: View) {
        view.findViewById<ImageView>(R.id.my_location)?.apply {
            setOnClickListener {
                presenter?.onMyLocationClick()
            }
        }

        addressView = view.findViewById(R.id.address)
        marker = view.findViewById(R.id.marker)
        permissionTopExplanation = view.findViewById<ViewGroup>(R.id.permission_container).apply {
            setOnClickListener {
                presenter?.onPermissionWindowClick()
            }
        }

        recents = view.findViewById<RecyclerView>(R.id.recents)?.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            adapter = RecentPlacesAdapter(
                view.context,
                presenter
            )
        }
    }

    companion object {
        val TAG = MapsFragment::class.java.simpleName
    }
}