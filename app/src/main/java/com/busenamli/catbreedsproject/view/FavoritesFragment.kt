package com.busenamli.catbreedsproject.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.busenamli.catbreedsproject.util.BreedFavStorage
import com.busenamli.catbreedsproject.viewmodel.FavoritesViewModel
import com.busenamli.catbreedsproject.R
import com.busenamli.catbreedsproject.adapter.FavoriteAdapter
import kotlinx.android.synthetic.main.favorites_fragment.*

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: FavoritesViewModel
    private val favoriteAdapter = FavoriteAdapter(arrayListOf())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        viewModel.getFavList(requireContext())

        favoriteRecyclerView.layoutManager = LinearLayoutManager(context)
        favoriteRecyclerView.adapter = favoriteAdapter

        swipeRefreshLayout.setOnRefreshListener {
            favoriteRecyclerView.visibility = View.GONE
            favoriteErrorText.visibility = View.GONE
            favoriteProgressBar.visibility = View.VISIBLE

            swipeRefreshLayout.isRefreshing = false
            viewModel.getFavList(requireContext())
        }

        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.favBreedsLiveData.observe(viewLifecycleOwner, Observer { breeds ->
            breeds?.let{
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