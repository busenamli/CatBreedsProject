package com.busenamli.catbreedsproject.util

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.busenamli.catbreedsproject.CurrentPage.currentPage

abstract class InfiniteScrollListener(var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    private var mLayoutManager = this.layoutManager


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        val totalItemCount = mLayoutManager!!.itemCount
        val lastVisibleItemPosition =
            (mLayoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition()

        if (totalItemCount == lastVisibleItemPosition + 1) {
            currentPage++
            println("Current Page:" + currentPage)
            onLoadMore(currentPage,totalItemCount,recyclerView)
            println("Load new list")
        }
    }

    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView)
}