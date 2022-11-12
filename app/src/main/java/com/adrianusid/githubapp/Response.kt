package com.adrianusid.githubapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Response(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<ItemsItem>,


    )
@Parcelize
data class ItemsItem(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    ) : Parcelable

@Parcelize
data class DetailUser(
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,

    @field:SerializedName("login")
    val login: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("company")
    val company: String?,

    @field:SerializedName("location")
    val location: String?,

    @field:SerializedName("public_repos")
    val repos: Int?

) : Parcelable

data class Follow(
    @field:SerializedName("login")
    val login: String?,

    @field:SerializedName("url")
    val url: String?,

    @field:SerializedName("avatar_url")
    val avatarUrl: String?,
)



