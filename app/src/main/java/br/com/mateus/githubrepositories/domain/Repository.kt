package br.com.mateus.githubrepositories.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository (
    val id: Int,
    val name: String,
    val description: String?,
    val imageUrl: String
) : Parcelable