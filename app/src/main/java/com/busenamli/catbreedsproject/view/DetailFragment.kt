package com.busenamli.catbreedsproject.view

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.busenamli.catbreedsproject.R
import com.busenamli.catbreedsproject.model.CatBreedModel
import com.busenamli.catbreedsproject.util.*
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    //private lateinit var viewModel: DetailViewModel
    private var breedModel: CatBreedModel? = null
    lateinit var breedFavStorage : BreedFavStorage
    var breedName: String? = null
    var breedId: String? = null
    var breedUrl: String? = null

    var adaptabilityFavImages = ArrayList<ImageView>()
    var affectionLevelFavImages = ArrayList<ImageView>()
    var intelligenceFavImages = ArrayList<ImageView>()
    var socialNeedsFavImages = ArrayList<ImageView>()

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

        setImageLists()

        /*viewModel  = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.getBreed()

        observeLiveData()*/

       arguments?.let {
           breedModel = DetailFragmentArgs.fromBundle(it).selectedBreed
           println("Breed Id: ${breedModel!!.catId}")

           breedUrl = DetailFragmentArgs.fromBundle(it).imageUrl
           println("Breed Url: ${breedUrl}")

           breedModel?.let {
               activity?.setTitle(it.catName)
               breedName = it.catName
               breedId = it.catId
               catNameText.text = it.catName
               if (breedUrl != null){
                   catImage.downloadImage(breedUrl,placeHolderProgressBar(view.context))
               }else {
                   catImage.downloadImage(it.catImage?.url, placeHolderProgressBar(view.context))
               }
               catTemperamentText.text = it.catTemperament
               catOriginText.text = it.catOrigin
               catDescriptionText.text = it.catDescription
               catLifespanText.text = it.catLifespan

               catHypoallergenicText.text = settingText(it.catHypoallergenic)
               catIndoorText.text = settingText(it.catIndoor)
               catLapText.text = settingText(it.catLap)

               settingFavoriteImages(adaptabilityFavImages,it.catAdaptability)
               settingFavoriteImages(affectionLevelFavImages,it.catAffectionLevel)
               settingFavoriteImages(intelligenceFavImages,it.catIntelligence)
               settingFavoriteImages(socialNeedsFavImages,it.catSocialNeeds)
           }
        }
    }

    fun setImageLists(){

        adaptabilityFavImages.add(catAdaptabilityImageView1)
        adaptabilityFavImages.add(catAdaptabilityImageView2)
        adaptabilityFavImages.add(catAdaptabilityImageView3)
        adaptabilityFavImages.add(catAdaptabilityImageView4)
        adaptabilityFavImages.add(catAdaptabilityImageView5)

        affectionLevelFavImages.add(catAffectionImageView1)
        affectionLevelFavImages.add(catAffectionImageView2)
        affectionLevelFavImages.add(catAffectionImageView3)
        affectionLevelFavImages.add(catAffectionImageView4)
        affectionLevelFavImages.add(catAffectionImageView5)

        intelligenceFavImages.add(catIntelligenceImageView1)
        intelligenceFavImages.add(catIntelligenceImageView2)
        intelligenceFavImages.add(catIntelligenceImageView3)
        intelligenceFavImages.add(catIntelligenceImageView4)
        intelligenceFavImages.add(catIntelligenceImageView5)

        socialNeedsFavImages.add(catSocialNeedsImageView1)
        socialNeedsFavImages.add(catSocialNeedsImageView2)
        socialNeedsFavImages.add(catSocialNeedsImageView3)
        socialNeedsFavImages.add(catSocialNeedsImageView4)
        socialNeedsFavImages.add(catSocialNeedsImageView5)

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
                     breedFavStorage.removeData(breedName,breedId)
                     println("favorilerden çıkarıldı")
                 }else{
                     item.setIcon(R.drawable.ic_fav)
                     breedFavStorage.saveData(breedName,breedId)
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