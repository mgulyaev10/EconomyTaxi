package com.helpfulproduction.economytaxi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.*

class MapsFragment: Fragment(), MapsContract.View<MapsContract.Presenter> {

    private val presenter = MapsPresenter(this)
    private var mapView: MapView? = null

    private var addressView: TextView? = null
    private var recents: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.maps_fragment, container, false)
        mapView = view.findViewById(R.id.map)
        mapView?.onCreate(savedInstanceState)

        initViews(view)

        presenter.onCreateView(mapView)
        return view
    }

    override fun onCameraMoveStarted() {
        addressView?.setGone()
    }

    override fun onUnknownAddress() {
        addressView?.setGone()
    }

    override fun onAddressResolved(address: String) {
        addressView?.text = address
        addressView?.setVisible()
    }

    override fun fragment() = this
    override fun activity() = activity

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onSaveInstanceState(p0: Bundle) {
        super.onSaveInstanceState(p0)
        mapView?.onSaveInstanceState(p0)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    private fun initViews(view: View?) {
        view?.let {
            view.findViewById<ImageView>(R.id.zoom_in)?.apply {
                setOnClickListener {
                    presenter.onZoomInClick()
                }
            }

            view.findViewById<ImageView>(R.id.zoom_out)?.apply {
                setOnClickListener {
                    presenter.onZoomOutClick()
                }
            }

            view.findViewById<ImageView>(R.id.my_location)?.apply {
                setOnClickListener {
                    presenter.onMyLocationClick()
                }
            }

            addressView = view.findViewById(R.id.address)

            recents = view.findViewById<RecyclerView>(R.id.recents)?.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = RecentPlacesAdapter(context)
            }
        }
    }

}