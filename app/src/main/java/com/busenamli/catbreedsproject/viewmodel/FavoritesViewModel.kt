package com.busenamli.catbreedsproject.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.busenamli.catbreedsproject.model.CatBreedWithImageSearchModel
import com.busenamli.catbreedsproject.service.CatBreedService
import com.busenamli.catbreedsproject.util.BreedFavStorage
import com.busenamli.catbreedsproject.util.sortingBreeds
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class FavoritesViewModel : ViewModel() {

    private val catBreedService = CatBreedService()
    private val disposable = CompositeDisposable()
    private lateinit var breedFavStorage : BreedFavStorage

    var favList: ArrayList<String?>? = null

    var favCatBreedList = ArrayList<CatBreedWithImageSearchModel>()
    val favBreedsLiveData = MutableLiveData<List<CatBreedWithImageSearchModel>>()
    val favErrorLiveData = MutableLiveData<Boolean>()
    val favLoadingLiveData = MutableLiveData<Boolean>()
    val isFavLiveData = MutableLiveData<Boolean>()

    fun getFavList(context: Context){

        breedFavStorage = BreedFavStorage(context)

        println("SIZE:" + (breedFavStorage.getArrayList(breedFavStorage.ARRAY_KEY)))

        if (breedFavStorage.getArrayList(breedFavStorage.ARRAY_KEY) != null) {
            favList = ArrayList(breedFavStorage.getArrayList(breedFavStorage.ARRAY_KEY))
            println("SIZE:" + favList?.size)
            getDataSearchFromAPI(favList)
        }
    }

    fun getDataSearchFromAPI(valueList: ArrayList<String?>?) {

        if (valueList.isNullOrEmpty()){
            isFavLiveData.value = true
            favErrorLiveData.value = false
        }else{
            favLoadingLiveData.value = true
        }

        favCatBreedList.clear()

        valueList?.forEach { value ->
            println(value)
            value?.let {
                disposable.add(
                    catBreedService.getCatImageBreedsBySearchData(it)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object: DisposableObserver<List<CatBreedWithImageSearchModel>>(){
                            override fun onNext(t: List<CatBreedWithImageSearchModel>) {

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
                                favBreedsLiveData.value = sortingBreeds(favCatBreedList)

                                if (favBreedsLiveData.value.isNullOrEmpty()){
                                    isFavLiveData.value = true
                                }
                            }

                        })
                )

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}