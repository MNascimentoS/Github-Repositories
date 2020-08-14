package br.com.mateus.githubrepositories.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository (
    val id: Int,
    val name: String,
    val description: String?,
    val ownerName: String,
    val ownerType: String,
    val imageUrl: String,
    val repositoryUrl: String,
    val stars: Int,
    val issues: Int,
    val watchers: Int,
    val forks: Int
) : Parcelable