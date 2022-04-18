package com.busenamli.catbreedsproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busenamli.catbreedsproject.model.CatBreedModel
import com.busenamli.catbreedsproject.model.Image

class DetailViewModel : ViewModel() {

    val breedLiveData = MutableLiveData<CatBreedModel>()

    fun getBreed(){

    }
}