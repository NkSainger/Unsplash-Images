package com.nk.searchimages.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nk.searchimages.adapter.ImagesAdapter
import com.nk.searchimages.databinding.ActivityMainBinding
import com.nk.searchimages.retrofit.ImagesApi
import com.nk.searchimages.retrofit.RetrofitHelper
import com.nk.searchimages.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageAdapter: ImagesAdapter
    private var oldQuery = ""
    var query = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var page = 0
        val perPage = 20
        val imagesList = ArrayList<String>()
        imageAdapter = ImagesAdapter(this, imagesList)
        binding.imagesRv.adapter = imageAdapter
        binding.imagesRv.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)

        val imagesApi = RetrofitHelper.getInstance().create(ImagesApi::class.java)

        binding.searchBtn.setOnClickListener {
            page = 0
            query = binding.searchView.text.toString().trim()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = imagesApi.getPhotos(
                        query,
                        0,
                        perPage,
                        Constants.API_KEY
                    )

                    if (query == oldQuery) {
                        result.results.forEach {
                            imagesList.add(it.urls.small)
                        }
                    } else {
                        imagesList.clear()
                        result.results.forEach {
                            imagesList.add(it.urls.small)
                        }
                    }

                    CoroutineScope(Dispatchers.Main).launch {
//                    binding.imagesRv.adapter = ImagesAdapter(this@MainActivity, imagesList)
                        imageAdapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@MainActivity, e.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }
            page++
        }

        binding.imagesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // Check if the RecyclerView is at the bottom
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                    val currentPosition = layoutManager.findLastVisibleItemPosition()
                    CoroutineScope(Dispatchers.IO).launch {
                        try{
                            val result = imagesApi.getPhotos(
                                query,
                                page,
                                perPage,
                                Constants.API_KEY
                            )

                            result.results.forEach {
                                imagesList.add(it.urls.small)
                            }

                            CoroutineScope(Dispatchers.Main).launch {
                                imageAdapter.notifyDataSetChanged()
                                if (currentPosition != RecyclerView.NO_POSITION) {
                                    recyclerView.scrollToPosition(currentPosition)
                                }
                            }
                        }  catch (e: Exception) {
                            CoroutineScope(Dispatchers.Main).launch {
                                Toast.makeText(this@MainActivity, e.message.toString(), Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    page++
                }
            }
        })
    }
}