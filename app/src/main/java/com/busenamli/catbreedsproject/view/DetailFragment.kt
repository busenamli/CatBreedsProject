package com.busenamli.catbreedsproject.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.busenamli.catbreedsproject.BreedFavStorage
import com.busenamli.catbreedsproject.viewmodel.DetailViewModel
import com.busenamli.catbreedsproject.R
import com.busenamli.catbreedsproject.model.CatBreedModel
import com.busenamli.catbreedsproject.util.downloadImage
import com.busenamli.catbreedsproject.util.placeHolderProgressBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    //private lateinit var viewModel: DetailViewModel
    private var breedModel: CatBreedModel? = null
    lateinit var breedFavStorage : BreedFavStorage
    var breedName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        breedFavStorage = BreedFavStorage(view.context)

        /*viewModel  = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.getBreed()

        observeLiveData()*/

       arguments?.let {
           breedModel = DetailFragmentArgs.fromBundle(it).selectedBreed
           println("Breed Id: ${breedModel!!.catId}")

           breedModel?.let {
               activity?.setTitle(it.catName)
               breedName = it.catName
               catNameText.text = it.catName
               catImage.downloadImage(it.catImage?.url, placeHolderProgressBar(view.context))
               catTemperamentText.text = it.catTemperament
               catOriginText.text = it.catOrigin
               catDescriptionText.text = it.catDescription
               catLifespanText.text = it.catLifespan
           }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fav,menu);
        if (breedFavStorage.isFav(breedName)){
            menu.findItem(R.id.fav_item).setIcon(R.drawable.ic_fav);
        }else{
            menu.findItem(R.id.fav_item).setIcon(R.drawable.ic_not_fav);
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        if (breedFavStorage.isFav(breedName)){
            menu.findItem(R.id.fav_item).setIcon(R.drawable.ic_fav);
        }else{
            menu.findItem(R.id.fav_item).setIcon(R.drawable.ic_not_fav);
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
             R.id.fav_item -> {
                 if (breedFavStorage.isFav(breedName)){
                     item.setIcon(R.drawable.ic_not_fav)
                     breedFavStorage.removeData(breedName)
                     println("favorilerden çıkarıldı")
                 }else{
                     item.setIcon(R.drawable.ic_fav)
                     breedFavStorage.saveData(breedName)
                     println("favorilere eklendi")
                 }
                 return true
             }
             else -> return super.onOptionsItemSelected(item)
         }
    }

    /*fun observeLiveData(){
        viewModel.breedLiveData.observe(viewLifecycleOwner, Observer { breed ->
            breed?.let {
                catNameText.text = breed.catName
                //catImage
                catTemperamentText.text = breed.catTemperament
                catOriginText.text = breed.catOrigin
                catDescriptionText.text = breed.catDescription
                catLifespanText.text = breed.catLifespan
            }
        })
    }*/
}