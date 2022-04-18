package com.busenamli.catbreedsproject.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.busenamli.catbreedsproject.BreedFavStorage
import com.busenamli.catbreedsproject.FavListFromStorage
import com.busenamli.catbreedsproject.viewmodel.FavoritesViewModel
import com.busenamli.catbreedsproject.R
import com.busenamli.catbreedsproject.adapter.BreedAdapter
import com.busenamli.catbreedsproject.adapter.FavoriteAdapter
import com.busenamli.catbreedsproject.model.CatBreedModel
import com.busenamli.catbreedsproject.util.sortingBreeds
import com.busenamli.catbreedsproject.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.favorites_fragment.*
import kotlinx.android.synthetic.main.home_fragment.*

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel
    private val favoriteAdapter = FavoriteAdapter(arrayListOf())
    lateinit var breedFavStorage : BreedFavStorage
    private lateinit var FavBreedModelList : ArrayList<CatBreedModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        breedFavStorage = BreedFavStorage(view.context)
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        if (breedFavStorage.getArrayList(breedFavStorage.ARRAY_KEY) != null) {
            FavListFromStorage.favListStorage =
                ArrayList(breedFavStorage.getArrayList(breedFavStorage.ARRAY_KEY))

            viewModel.getDataSearchFromAPI(FavListFromStorage.favListStorage)
        }

        /*FavListFromStorage.favListStorage?.forEach {
            println(it)
            viewModel.getDataSearchFromAPI(it)
        }*/

        favoriteRecyclerView.layoutManager = LinearLayoutManager(context)
        favoriteRecyclerView.adapter = favoriteAdapter

        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.favBreedsLiveData.observe(viewLifecycleOwner, Observer { breeds ->
            breeds?.let{
                sortingBreeds(breeds)
                favoriteRecyclerView.visibility = View.VISIBLE
                favoriteAdapter.updateBreedList(breeds)
            }
        })

        viewModel.favErrorLiveData.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if (it){
                    favoriteErrorText.visibility = View.VISIBLE
                    favoriteRecyclerView.visibility = View.GONE
                    favoriteProgressBar.visibility = View.GONE
                    favoriteEmptyText.visibility = View.GONE
                }else{
                    favoriteErrorText.visibility = View.GONE
                }
            }
        })

        viewModel.favLoadingLiveData.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if(it){
                    favoriteProgressBar.visibility = View.VISIBLE
                    favoriteRecyclerView.visibility = View.GONE
                    favoriteErrorText.visibility = View.GONE
                    favoriteEmptyText.visibility = View.GONE
                }else{
                    favoriteProgressBar.visibility = View.GONE
                }
            }
        })

        viewModel.isFavLiveData.observe(viewLifecycleOwner, Observer { isFav ->
            isFav?.let {
                if (it){
                    favoriteEmptyText.visibility = View.VISIBLE
                    favoriteProgressBar.visibility = View.GONE
                    favoriteRecyclerView.visibility = View.GONE
                    favoriteErrorText.visibility = View.GONE
                }else{
                    favoriteEmptyText.visibility = View.GONE
                }
            }
        })
    }

}