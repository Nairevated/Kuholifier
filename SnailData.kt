package com.example.kuholifier

import android.os.Parcel
import android.os.Parcelable

data class SnailData(
    val image:Int,
    val name:String,
    val scname: String,
    val toxicology: String)

    : Parcelable {
        constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(name)
        parcel.writeString(scname)
        parcel.writeString(toxicology)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SnailData> {
        override fun createFromParcel(parcel: Parcel): SnailData {
            return SnailData(parcel)
        }

        override fun newArray(size: Int): Array<SnailData?> {
            return arrayOfNulls(size)
        }
    }
}
