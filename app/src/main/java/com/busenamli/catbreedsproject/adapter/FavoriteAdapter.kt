package com.busenamli.catbreedsproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.busenamli.catbreedsproject.util.BreedFavStorage
import com.busenamli.catbreedsproject.R
import com.busenamli.catbreedsproject.model.CatBreedWithImageSearchModel
import com.busenamli.catbreedsproject.util.downloadImage
import com.busenamli.catbreedsproject.util.placeHolderProgressBar
import com.busenamli.catbreedsproject.view.FavoritesFragmentDirections
import kotlinx.android.synthetic.main.favorite_item_row.view.*

class FavoriteAdapter(val favoriteCatBreedList: ArrayList<CatBreedWithImageSearchModel>):
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

        var breedName = favoriteCatBreedList.get(position).getSelectedCatBreed()?.get(0)?.catName
        println("Breed Name: " + breedName)
        var breedId = favoriteCatBreedList.get(position).getSelectedCatBreed()?.get(0)?.catId
        println("Breed ID" + breedId)

        holder.itemView.favBreedItemNameText.text = favoriteCatBreedList.get(position).getSelectedCatBreed()?.get(0)?.catName

        println("Breed URL: " +favoriteCatBreedList.get(position).url)

        holder.itemView.favBreedItemImageView.downloadImage(
            favoriteCatBreedList.get(position).url,
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
                breedFavStorage.removeData(breedName,breedId);
            }else{
                holder.itemView.favBreedItemFavImageButton.setImageResource(R.drawable.ic_fav);
                breedFavStorage.saveData(breedName,breedId);
            }
        }

        holder.itemView.setOnClickListener {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(
                favoriteCatBreedList.get(position).getSelectedCatBreed()?.get(0),
                favoriteCatBreedList.get(position).url)
            //println("Cat Id: " + favoriteCatBreedList.get(position).getSelectedCatBreed()?.get(0)?.catId)
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        //println("size:" + favoriteCatBreedList.size)
        return favoriteCatBreedList.size
    }

    fun updateBreedList(newFavBreedList: List<CatBreedWithImageSearchModel> ){
        favoriteCatBreedList.clear()
        favoriteCatBreedList.addAll(newFavBreedList)
        notifyDataSetChanged()
    }
}