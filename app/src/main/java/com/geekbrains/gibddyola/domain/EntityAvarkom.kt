package com.geekbrains.gibddyola.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EntityAvarkom(
    val idAvarkom: Int,
    val textName: String,
    val textAbout: String,
    val textRaiting: String,
    val avatar: String
) : Parcelable
