package com.busenamli.catbreedsproject.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CatBreedWithImageSearchModel(
    @SerializedName("breeds")
    val breeds: List<CatBreedModel?>? = null,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("width")
    val width: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("url")
    val url: String?
):Serializable{

    fun getSelectedCatBreed(): List<CatBreedModel?>? {
        return breeds
    }
}