package com.geekbrains.gibddyola.domain.employee

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class EntityAvarkom(
    val idAvarkom: Int,
    val textName: String,
    val textAbout: String,
    val textJobYear: String,
    val avatar: Int?
) : Parcelable
