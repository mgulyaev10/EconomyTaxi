package com.helpfulproduction.economytaxi.models

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng

data class Place(
    val coordinates: LatLng,
    val address: String
): Parcelable {

    private constructor(parcel: Parcel): this(
        LatLng(
            parcel.readDouble(),
            parcel.readDouble()
        ),
        parcel.readString()!!
    )

    override fun describeContents(): Int = 0
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeDouble(coordinates.latitude)
        dest?.writeDouble(coordinates.longitude)
        dest?.writeString(address)
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Place> {
            override fun createFromParcel(source: Parcel): Place {
                return Place(source)
            }

            override fun newArray(size: Int): Array<Place?> {
                return arrayOfNulls(size)
            }
        }
    }
}