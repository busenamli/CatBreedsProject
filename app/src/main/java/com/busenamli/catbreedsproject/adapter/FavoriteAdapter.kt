package com.busenamli.catbreedsproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.busenamli.catbreedsproject.BreedFavStorage
import com.busenamli.catbreedsproject.R
import com.busenamli.catbreedsproject.model.CatBreedModel
import com.busenamli.catbreedsproject.util.downloadImage
import com.busenamli.catbreedsproject.util.placeHolderProgressBar
import com.busenamli.catbreedsproject.view.FavoritesFragmentDirections
import com.busenamli.catbreedsproject.view.HomeFragmentDirections
import kotlinx.android.synthetic.main.breed_item_row.view.*
import kotlinx.android.synthetic.main.favorite_item_row.view.*

class FavoriteAdapter(val favoriteCatBreedList: ArrayList<CatBreedModel>):
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>()  {

    lateinit var breedFavStorage : BreedFavStorage

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_item_row,parent,false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {

        breedFavStorage = BreedFavStorage(holder.itemView.context)

        var breedName = favoriteCatBreedList.get(position).catName

        holder.itemView.favBreedItemNameText.text = favoriteCatBreedList.get(position).catName

        println(favoriteCatBreedList.get(position).catImage?.url)

        holder.itemView.favBreedItemImageView.downloadImage(
            favoriteCatBreedList.get(position).catImage?.url,
            placeHolderProgressBar(holder.itemView.context)
        )

        if (breedFavStorage.isFav(breedName)){
            holder.itemView.favBreedItemFavImageButton.setImageResource(R.drawable.ic_fav)
        }else{
            holder.itemView.favBreedItemFavImageButton.setImageResource(R.drawable.ic_not_fav);
        }

        holder.itemView.favBreedItemFavImageButton.setOnClickListener {
            if (breedFavStorage.isFav(breedName)){
                holder.itemView.favBreedItemFavImageButton.setImageResource(R.drawable.ic_not_fav);
                breedFavStorage.removeData(breedName);
            }else{
                holder.itemView.favBreedItemFavImageButton.setImageResource(R.drawable.ic_fav);
                breedFavStorage.saveData(breedName);
            }
        }

        holder.itemView.setOnClickListener {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(favoriteCatBreedList.get(position))
            println("Cat Id: " + favoriteCatBreedList.get(position).catId)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        println("size:" + favoriteCatBreedList.size)
        return favoriteCatBreedList.size
    }

    fun updateBreedList(newFavBreedList: List<CatBreedModel> ){
        favoriteCatBreedList.clear()
        favoriteCatBreedList.addAll(newFavBreedList)
        notifyDataSetChanged()
    }
}