package com.busenamli.catbreedsproject.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.busenamli.catbreedsproject.R
import com.busenamli.catbreedsproject.model.CatBreedModel
import com.busenamli.catbreedsproject.model.CatBreedWithImageSearchModel

//Extension
fun ImageView.downloadImage(url: String?, progressDrawable: CircularProgressDrawable){

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun placeHolderProgressBar(context: Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

fun sortingBreeds(catBreedList : List<CatBreedWithImageSearchModel>) : List<CatBreedWithImageSearchModel>{
    val sortedCatBreedList = catBreedList.sortedWith(
        compareBy({ it.getSelectedCatBreed()?.get(0)?.catName })
    )
    return sortedCatBreedList
}

fun settingText(value: Int?): String{
    if (value == 0){
        return "No"
    }else{
        return "Yes"
    }
}

fun settingFavoriteImages(images : ArrayList<ImageView>, value: Int?){

    when(value){
        1 -> images.get(0).setImageResource(R.drawable.ic_fav)

        2 -> {images.get(0).setImageResource(R.drawable.ic_fav)
            images.get(1).setImageResource(R.drawable.ic_fav)
        }
        3 -> {images.get(0).setImageResource(R.drawable.ic_fav)
            images.get(1).setImageResource(R.drawable.ic_fav)
            images.get(2).setImageResource(R.drawable.ic_fav)
        }
        4 -> {images.get(0).setImageResource(R.drawable.ic_fav)
            images.get(1).setImageResource(R.drawable.ic_fav)
            images.get(2).setImageResource(R.drawable.ic_fav)
            images.get(3).setImageResource(R.drawable.ic_fav)
        }

        5-> {images.get(0).setImageResource(R.drawable.ic_fav)
            images.get(1).setImageResource(R.drawable.ic_fav)
            images.get(2).setImageResource(R.drawable.ic_fav)
            images.get(3).setImageResource(R.drawable.ic_fav)
            images.get(4).setImageResource(R.drawable.ic_fav)
        }

        else -> {images.forEach {
            it.setImageResource(R.drawable.ic_not_fav) }
        }
    }
}
