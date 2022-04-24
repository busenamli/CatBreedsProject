package com.busenamli.catbreedsproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.busenamli.catbreedsproject.util.BreedFavStorage
import com.busenamli.catbreedsproject.CurrentPage.currentPage
import com.busenamli.catbreedsproject.R
import com.busenamli.catbreedsproject.model.CatBreedModel
import com.busenamli.catbreedsproject.model.CatBreedWithImageSearchModel
import com.busenamli.catbreedsproject.util.downloadImage
import com.busenamli.catbreedsproject.util.placeHolderProgressBar
import com.busenamli.catbreedsproject.view.HomeFragmentDirections
import kotlinx.android.synthetic.main.breed_item_row.view.*

class BreedAdapter(val catBreedList: ArrayList<CatBreedModel>):
    RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {

    lateinit var breedFavStorage : BreedFavStorage

    class BreedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.breed_item_row,parent,false)
        return BreedViewHolder(view)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {

        breedFavStorage = BreedFavStorage(holder.itemView.context)

        var breedName = catBreedList.get(position).catName
        var breedId = catBreedList.get(position).catId

        holder.itemView.breedItemNameText.text = catBreedList.get(position).catName

        println(catBreedList.get(position).catImage?.url)

        holder.itemView.breedItemImageView.downloadImage(
            catBreedList.get(position).catImage?.url,
            placeHolderProgressBar(holder.itemView.context))


        if (breedFavStorage.isFav(breedName)){
            holder.itemView.breedItemFavImageButton.setImageResource(R.drawable.ic_fav)
        }else{
            holder.itemView.breedItemFavImageButton.setImageResource(R.drawable.ic_not_fav);
        }

        holder.itemView.breedItemFavImageButton.setOnClickListener {
            if (breedFavStorage.isFav(breedName)){
                println("Id: "+ breedId)
                holder.itemView.breedItemFavImageButton.setImageResource(R.drawable.ic_not_fav);
                breedFavStorage.removeData(breedName,breedId);
            }else{
                println("Id: " + breedId)
                holder.itemView.breedItemFavImageButton.setImageResource(R.drawable.ic_fav);
                breedFavStorage.saveData(breedName,breedId);
            }
        }

        holder.itemView.setOnClickListener {
            currentPage = 0
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(catBreedList.get(position),null)
            println("Cat Id: " + catBreedList.get(position).catId)
            Navigation.findNavController(it).navigate(action)
            println(catBreedList.size)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return catBreedList.size
    }

    fun updateBreedList(newCatBreedList: List<CatBreedModel> ){
        if (currentPage == 0){
            catBreedList.clear()
            catBreedList.addAll(newCatBreedList)
            notifyDataSetChanged()
        }else{
            catBreedList.addAll(newCatBreedList)
            notifyDataSetChanged()
        }

    }
}