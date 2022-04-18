package com.busenamli.catbreedsproject.model

import com.busenamli.catbreedsproject.model.Image
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CatBreedModel(
    @SerializedName("id")
    val catId: String?,
    @SerializedName("image")
    val catImage: Image?,
    @SerializedName("name")
    val catName: String?,
    @SerializedName("life_span")
    val catLifespan: String?,
    @SerializedName("origin")
    val catOrigin: String?,
    @SerializedName("temperament")
    val catTemperament: String?,
    @SerializedName("description")
    val catDescription: String?,
) : Serializable