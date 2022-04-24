package com.busenamli.catbreedsproject.util

import android.content.Context
import android.content.SharedPreferences
import com.busenamli.catbreedsproject.FavListFromStorage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class BreedFavStorage (context: Context){

    var ARRAY_KEY = "FavArrayList"
    var sharedPreferences = context.getSharedPreferences("com.busenamli.catbreedsproject", Context.MODE_PRIVATE)

    fun saveData(breedName: String?, breedId: String?) {
        println(breedName)
        breedName?.let {
            sharedPreferences.edit().putString(it, it).apply()
            println(it)

            breedId?.let { id->
                if (getArrayList(ARRAY_KEY) != null){
                    FavListFromStorage.favListStorage = ArrayList (getArrayList(ARRAY_KEY))
                    FavListFromStorage.favListStorage!!.add(id)
                    saveArrayList(FavListFromStorage.favListStorage,ARRAY_KEY)
                    println(FavListFromStorage.favListStorage!!.size)
                }else{
                    FavListFromStorage.favListStorage = ArrayList()
                    FavListFromStorage.favListStorage!!.add(id)
                    saveArrayList(FavListFromStorage.favListStorage,ARRAY_KEY)
                    println(FavListFromStorage.favListStorage!!.size)
                }
            }
        }
    }

    fun removeData(breedName: String?, breedId: String?) {
        val favBreedName = sharedPreferences.getString(breedName, "")
        if (favBreedName != "") {
            sharedPreferences.edit().remove(breedName).apply()

            breedId?.let {id->
                if(getArrayList(ARRAY_KEY) != null){
                    FavListFromStorage.favListStorage = ArrayList (getArrayList(ARRAY_KEY))
                    //FavListFromStorage.favListStorage = getArrayList(ARRAY_KEY)
                    FavListFromStorage.favListStorage!!.remove(id)
                    saveArrayList(FavListFromStorage.favListStorage,ARRAY_KEY)
                    println(FavListFromStorage.favListStorage!!.size)
                }
            }
        }
    }

    fun isFav(breedName: String?): Boolean {
        val favBreedName = sharedPreferences.getString(breedName, "")
        return if (favBreedName != "") {
            true
        } else false
    }

    fun saveArrayList(list: ArrayList<String?>?, key: String?) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getArrayList(key: String?): ArrayList<String?>? {
        val gson = Gson()
        val json: String? = sharedPreferences.getString(key, null)
        val type: Type = object : TypeToken<java.util.ArrayList<String?>?>() {}.getType()
        return gson.fromJson(json, type)
    }

}