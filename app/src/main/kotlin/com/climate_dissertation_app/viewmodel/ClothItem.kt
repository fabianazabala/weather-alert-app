package com.climate_dissertation_app.viewmodel

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes

data class ClothItem(
    @DrawableRes val resourceId: Int,
    val name: String,
    val clothPosition: ClothPosition
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString()?.let { ClothPosition.valueOf(it) } ?: ClothPosition.ACCESSORY
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(resourceId)
        parcel.writeString(name)
        parcel.writeString(clothPosition.name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ClothItem> {
        override fun createFromParcel(parcel: Parcel): ClothItem {
            return ClothItem(parcel)
        }

        override fun newArray(size: Int): Array<ClothItem?> {
            return arrayOfNulls(size)
        }
    }
}

enum class ClothPosition {
    HEAD,
    TORSO,
    LEGS,
    FEET,
    ACCESSORY
}