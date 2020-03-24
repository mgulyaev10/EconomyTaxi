package com.helpfulproduction.economytaxi.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.helpfulproduction.economytaxi.R
import com.helpfulproduction.economytaxi.adapters.PlacesSearchAdapter
import com.helpfulproduction.economytaxi.interfaces.SearchPlaceClickListener
import com.helpfulproduction.economytaxi.models.Place

class BottomSheetSearchFragment: BottomSheetDialogFragment(), SearchPlaceClickListener {

    private var from: EditText? = null
    private var placeFrom: Place? = null
    private var to: EditText? = null
    private var placeTo: Place? = null

    private var placesRecycler: RecyclerView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setContentView(R.layout.fragment_search_bottom_sheet)
        return dialog
    }

    override fun onPlaceClick() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        placeFrom = arguments?.getParcelable(KEY_PLACE) as? Place
        from = dialog!!.findViewById(R.id.from)
        to = dialog!!.findViewById(R.id.to)
        placesRecycler = dialog?.findViewById<RecyclerView>(R.id.places_list_recycler)?.apply {
            layoutManager = LinearLayoutManager(dialog!!.context, LinearLayoutManager.VERTICAL, false)
            adapter = PlacesSearchAdapter(this@BottomSheetSearchFragment)
        }
        from?.setText(placeFrom?.address ?: "")

        return view
    }

    class Builder {
        private val args = Bundle()

        fun setPlaceFrom(from: Place) = apply {
            args.putParcelable(KEY_PLACE, from)
        }

        fun build(): BottomSheetSearchFragment {
            return BottomSheetSearchFragment().apply {
                arguments = args
            }
        }
    }

    companion object {
        val TAG = BottomSheetSearchFragment::class.java.simpleName

        private const val KEY_PLACE = "key_place"
    }

}