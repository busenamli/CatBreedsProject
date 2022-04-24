package com.busenamli.catbreedsproject.service

import com.busenamli.catbreedsproject.Constants
import com.busenamli.catbreedsproject.Constants.API_KEY
import com.busenamli.catbreedsproject.Constants.BASE_URL
import com.busenamli.catbreedsproject.model.CatBreedModel
import com.busenamli.catbreedsproject.model.CatBreedWithImageSearchModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CatBreedService {

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

    fun getCatImageBreedsBySearchData(breed_id: String):Observable<List<CatBreedWithImageSearchModel>>{
        return api.CatBreedWithImageBySearch(API_KEY,breed_id)
    }
}