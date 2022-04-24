package com.busenamli.catbreedsproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busenamli.catbreedsproject.model.CatBreedModel
import com.busenamli.catbreedsproject.service.CatBreedService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class HomeViewModel : ViewModel() {

    private val catBreedService = CatBreedService()
    private val disposable = CompositeDisposable()

    val breedsLiveData = MutableLiveData<List<CatBreedModel>>()
    val homeErrorLiveData = MutableLiveData<Boolean>()
    val homeLoadingLiveData = MutableLiveData<Boolean>()

    fun getDataFromAPI(limit: String, page: String){
        homeLoadingLiveData.value = true

        disposable.add(
            catBreedService.getCatBreedsData(limit,page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<CatBreedModel>>(){
                    override fun onNext(t: List<CatBreedModel>) {
                        breedsLiveData.value = t
                    }

                    override fun onError(e: Throwable) {
                        homeErrorLiveData.value = true
                        homeLoadingLiveData.value = false
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        homeErrorLiveData.value = false
                        homeLoadingLiveData.value = false
                    }

                })
        )
    }

    fun getDataSearchFromAPI(value: String) {
        homeLoadingLiveData.value = true

        disposable.add(
            catBreedService.getCatBreedsBySearchData(value)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<CatBreedModel>>(){
                    override fun onNext(t: List<CatBreedModel>) {
                        breedsLiveData.value = t
                    }

                    override fun onError(e: Throwable) {
                        homeErrorLiveData.value = true
                        homeLoadingLiveData.value = false
                        e.printStackTrace()
                    }

                    override fun onComplete() {
                        homeErrorLiveData.value = false
                        homeLoadingLiveData.value = false
                    }

                })
        )
    }
}