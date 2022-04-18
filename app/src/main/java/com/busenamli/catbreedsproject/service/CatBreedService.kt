package com.busenamli.catbreedsproject.service

import com.busenamli.catbreedsproject.model.CatBreedModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CatBreedService {

    //https://api.thecatapi.com/v1/breeds?limit=15&page=1
    //x-api-key = 916a93dd-3507-45f7-b431-36bd224f1700
    //https://api.thecatapi.com/v1/breeds/search?q=abyssinian

    private val API_KEY = "916a93dd-3507-45f7-b431-36bd224f1700"
    private val BASE_URL = "https://api.thecatapi.com/v1/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CatBreedAPI::class.java)

    fun getCatBreedsData(limit:String, page: String): Observable<List<CatBreedModel>>{
        return api.getCatBreeds(API_KEY,limit,page)
    }

    fun getCatBreedsBySearchData(q: String):Observable<List<CatBreedModel>>{
        return api.getCatBreedsBySearch(API_KEY,q)
    }
}