package com.busenamli.catbreedsproject.service

import com.busenamli.catbreedsproject.Constants.GET_BREEDS_BY_SEARCH_KEY
import com.busenamli.catbreedsproject.Constants.GET_BREEDS_KEY
import com.busenamli.catbreedsproject.Constants.GET_BREED_WITH_IMAGE_BY_SEARCH_KEY
import com.busenamli.catbreedsproject.model.CatBreedModel
import com.busenamli.catbreedsproject.model.CatBreedWithImageSearchModel
import com.busenamli.catbreedsproject.model.Image
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CatBreedAPI {

    @GET(GET_BREEDS_KEY)
    fun getCatBreeds(@Header("x-api-key") apiKey: String,
                     @Query("limit") limit:String,
                     @Query("page") page:String
    ): Observable<List<CatBreedModel>>

    @GET(GET_BREEDS_BY_SEARCH_KEY)
    fun getCatBreedsBySearch(@Header("x-api-key") apiKey: String,
                             @Query("q") q:String
    ):Observable<List<CatBreedModel>>

    @GET(GET_BREED_WITH_IMAGE_BY_SEARCH_KEY)
    fun CatBreedWithImageBySearch(@Header("x-api-key") apiKey: String,
                                   @Query("breed_id") breed_id:String
    ):Observable<List<CatBreedWithImageSearchModel>>
}