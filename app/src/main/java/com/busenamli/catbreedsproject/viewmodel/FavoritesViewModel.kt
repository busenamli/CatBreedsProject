package com.busenamli.catbreedsproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busenamli.catbreedsproject.model.CatBreedModel
import com.busenamli.catbreedsproject.service.CatBreedService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class FavoritesViewModel : ViewModel() {

    private val catBreedService = CatBreedService()
    private val disposable = CompositeDisposable()

    var favCatBreedList = ArrayList<CatBreedModel>()
    val favBreedsLiveData = MutableLiveData<List<CatBreedModel>>()
    val favErrorLiveData = MutableLiveData<Boolean>()
    val favLoadingLiveData = MutableLiveData<Boolean>()
    val isFavLiveData = MutableLiveData<Boolean>()

    fun getDataSearchFromAPI(valueList: ArrayList<String?>?) {
        favLoadingLiveData.value = true

        //favCatBreedList.clear()

        valueList?.forEach { value ->
            value?.let {
                disposable.add(
                    catBreedService.getCatBreedsBySearchData(it)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableObserver<List<CatBreedModel>>(){
                            override fun onNext(t: List<CatBreedModel>) {
                                if (t.size == 1){
                                    favCatBreedList.add(t.get(0))
                                }
                            }

                            override fun onError(e: Throwable) {
                                favErrorLiveData.value = true
                                favLoadingLiveData.value = false
                                isFavLiveData.value = false
                                e.printStackTrace()
                            }

                            override fun onComplete() {
                                favErrorLiveData.value = false
                                favLoadingLiveData.value = false
                                isFavLiveData.value = false
                                favBreedsLiveData.value = favCatBreedList

                                if (favBreedsLiveData.value.isNullOrEmpty()){
                                    isFavLiveData.value = true
                                }
                            }

                        })
                )
            }

        }

    }
}