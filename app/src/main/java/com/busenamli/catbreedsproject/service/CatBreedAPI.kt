package com.busenamli.catbreedsproject.service

import com.busenamli.catbreedsproject.model.CatBreedModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CatBreedAPI {

    //https://api.thecatapi.com/v1/breeds?limit=15&page=1
    //https://api.thecatapi.com/v1/breeds/search?q=abyssinian

    @GET("breeds")
    fun getCatBreeds(@Header("x-api-key") apiKey: String,
                     @Query("limit") limit:String,
                     @Query("page") page:String
    ): Observable<List<CatBreedModel>>

    @GET("breeds/search")
    fun getCatBreedsBySearch(@Header("x-api-key") apiKey: String,
                             @Query("q") q:String
    ):Observable<List<CatBreedModel>>
}