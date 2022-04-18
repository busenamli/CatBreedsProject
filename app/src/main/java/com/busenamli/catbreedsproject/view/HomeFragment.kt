package com.busenamli.catbreedsproject.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.busenamli.catbreedsproject.BreedFavStorage
import com.busenamli.catbreedsproject.FavListFromStorage
import com.busenamli.catbreedsproject.viewmodel.HomeViewModel
import com.busenamli.catbreedsproject.R
import com.busenamli.catbreedsproject.adapter.BreedAdapter
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private val breedAdapter = BreedAdapter(arrayListOf())
    lateinit var breedFavStorage : BreedFavStorage


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        breedFavStorage = BreedFavStorage(view.context)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getDataFromAPI("20","0")

        searchButton.setOnClickListener {

            var searchText = searchEditText.text.toString()
            println("searchtext: " + searchText)

            if(searchText == null || searchText == ""){
                viewModel.getDataFromAPI("15","0")
            }else{
                viewModel.getDataSearchFromAPI(searchText)
            }
        }

        breedRecyclerView.layoutManager = LinearLayoutManager(context)
        breedRecyclerView.adapter = breedAdapter

        observeLiveData()

    }

    override fun onResume() {
        super.onResume()
        searchButton.setOnClickListener {

            var searchText = searchEditText.text.toString()
            println("searchtext: " + searchText)

            if(searchText == null || searchText == ""){
                viewModel.getDataFromAPI("15","0")
            }else{
                viewModel.getDataSearchFromAPI(searchText)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fav_fragment,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.fav_fragment_item -> {
                println("tıklandı")
                val action = HomeFragmentDirections.actionHomeFragmentToFavoritesFragment()
                Navigation.findNavController(requireView()).navigate(action)
                return true
            }else -> return super.onOptionsItemSelected(item)
        }
    }

    fun observeLiveData(){
        viewModel.breedsLiveData.observe(viewLifecycleOwner, Observer { breeds ->
            breeds?.let {
                breedRecyclerView.visibility = View.VISIBLE
                searchEditText.visibility = View.VISIBLE
                searchButton.visibility = View.VISIBLE
                breedAdapter.updateBreedList(breeds)
            }
        })

        viewModel.homeErrorLiveData.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if (it){
                    homeErrorText.visibility = View.VISIBLE
                    breedRecyclerView.visibility = View.GONE
                    homeProgressBar.visibility = View.GONE
                    searchEditText.visibility = View.GONE
                    searchButton.visibility = View.GONE
                }else{
                    homeErrorText.visibility = View.GONE
                }
            }
        })

        viewModel.homeLoadingLiveData.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if(it){
                    homeProgressBar.visibility = View.VISIBLE
                    breedRecyclerView.visibility = View.GONE
                    homeErrorText.visibility = View.GONE
                    searchEditText.visibility = View.GONE
                    searchButton.visibility = View.GONE
                }else{
                    homeProgressBar.visibility = View.GONE
                }
            }
        })
    }

}