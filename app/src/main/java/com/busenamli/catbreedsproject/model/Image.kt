package com.busenamli.catbreedsproject.model

import com.google.gson.annotations.SerializedName

data class Image (
    @SerializedName("height")
    val height: Int?,
    @SerializedName("width")
    val width: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("url")
    val url: String?
)