package com.example.kuholifier

import android.os.Parcel
import android.os.Parcelable

data class RecipeData(
    val recipeImg: Int,
    val recipeName: String,
    val recipeIngredient: String,
    val recipePrep: String,
    val recipeCook: String)

    : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(recipeImg)
        parcel.writeString(recipeName)
        parcel.writeString(recipeIngredient)
        parcel.writeString(recipePrep)
        parcel.writeString(recipeCook)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecipeData> {
        override fun createFromParcel(parcel: Parcel): RecipeData {
            return RecipeData(parcel)
        }

        override fun newArray(size: Int): Array<RecipeData?> {
            return arrayOfNulls(size)
        }
    }
}