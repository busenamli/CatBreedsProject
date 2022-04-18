package com.busenamli.catbreedsproject

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.busenamli.catbreedsproject.model.CatBreedModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class BreedFavStorage (context: Context){

    var ARRAY_KEY = "FavArrayList"
    var sharedPreferences = context.getSharedPreferences("com.busenamli.catbreedsproject", Context.MODE_PRIVATE)

    fun saveData(breedName: String?) {
        println(breedName)
        breedName?.let {
            sharedPreferences.edit().putString(it, it).apply()
            println(it)

            if (getArrayList(ARRAY_KEY) != null){
                FavListFromStorage.favListStorage = ArrayList (getArrayList(ARRAY_KEY))
                FavListFromStorage.favListStorage!!.add(it)
                saveArrayList(FavListFromStorage.favListStorage,ARRAY_KEY)
                println(FavListFromStorage.favListStorage!!.size)
            }else{
                FavListFromStorage.favListStorage = ArrayList()
                FavListFromStorage.favListStorage!!.add(it)
                saveArrayList(FavListFromStorage.favListStorage,ARRAY_KEY)
                println(FavListFromStorage.favListStorage!!.size)
            }
        }
    }

    fun removeData(breedName: String?) {
        val favBreedName = sharedPreferences.getString(breedName, "")
        if (favBreedName != "") {
            sharedPreferences.edit().remove(breedName).apply()

            if(getArrayList(ARRAY_KEY) != null){
                FavListFromStorage.favListStorage = ArrayList (getArrayList(ARRAY_KEY))
                //FavListFromStorage.favListStorage = getArrayList(ARRAY_KEY)
                FavListFromStorage.favListStorage!!.remove(favBreedName)
                saveArrayList(FavListFromStorage.favListStorage,ARRAY_KEY)
                println(FavListFromStorage.favListStorage!!.size)
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

    /*fun returnFavNameList(): ArrayList<String>? {
        return favNameList.favBreedNameList
    }*/

}