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
    @SerializedName("indoor")
    val catIndoor: Int?,
    @SerializedName("lap")
    val catLap: Int?,
    @SerializedName("hypoallergenic")
    val catHypoallergenic : Int?,
    @SerializedName("adaptability")
    val catAdaptability : Int?,
    @SerializedName("affection_level")
    val catAffectionLevel : Int?,
    @SerializedName("intelligence")
    val catIntelligence : Int?,
    @SerializedName("social_needs")
    val catSocialNeeds : Int?,
) : Serializable