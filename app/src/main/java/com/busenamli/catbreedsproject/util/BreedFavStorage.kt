package com.busenamli.catbreedsproject.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class BreedFavStorage {

    var ARRAY_KEY = "FavArrayList"
    var favList: ArrayList<String?>? = null

    companion object {

        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var instance: BreedFavStorage? = null

        private val lock = Any()
        operator fun invoke(context: Context): BreedFavStorage = instance ?: synchronized(lock) {
            instance ?: makeBreedFavStorage(context).also {
                instance = it
            }
        }

        private fun makeBreedFavStorage(context: Context): BreedFavStorage{
            sharedPreferences = context.getSharedPreferences("com.busenamli.catbreedsproject", Context.MODE_PRIVATE)
            return BreedFavStorage()

        }
    }

    fun saveData(breedName: String?, breedId: String?) {
        println(breedName)
        breedName?.let {
            sharedPreferences?.edit()?.putString(it, it)?.apply()
            println(it)

            breedId?.let { id->
                if (getArrayList(ARRAY_KEY) != null){
                    favList = getArrayList(ARRAY_KEY)
                    favList?.add(id)
                    saveArrayList(favList,ARRAY_KEY)

                }else{
                    favList = ArrayList()
                    favList?.add(id)
                    saveArrayList(favList,ARRAY_KEY)
                }
            }
        }
    }

    fun removeData(breedName: String?, breedId: String?) {
        val favBreedName = sharedPreferences?.getString(breedName, "")
        if (favBreedName != "") {
            sharedPreferences?.edit()?.remove(breedName)?.apply()

            breedId?.let {id->
                if(getArrayList(ARRAY_KEY) != null){
                    favList = getArrayList(ARRAY_KEY)
                    favList?.remove(id)
                    saveArrayList(favList,ARRAY_KEY)
                }
            }
        }
    }

    fun isFav(breedName: String?): Boolean {
        val favBreedName = sharedPreferences?.getString(breedName, "")
        return if (favBreedName != "") {
            true
        } else false
    }

    fun saveArrayList(list: ArrayList<String?>?, key: String?) {
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor?.let {
            it.putString(key, json)
            it.apply()
        }
    }

    fun getArrayList(key: String?): ArrayList<String?>? {
        val gson = Gson()
        val json: String? = sharedPreferences?.getString(key, null)
        val type: Type = object : TypeToken<java.util.ArrayList<String?>?>() {}.getType()
        return gson.fromJson(json, type)
    }

}