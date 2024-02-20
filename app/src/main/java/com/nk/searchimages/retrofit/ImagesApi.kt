package com.nk.searchimages.retrofit

import com.nk.searchimages.datamodel.ImageDataModal
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {
    @GET("search/photos")
    suspend fun getPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("client_id") apiKey: String
    ): ImageDataModal
}

//https://api.unsplash.com/search/photos?query=brown&page=1&per_page=10&client_id=x1B6EBPuxyjcK6DoYvowC_1muUO1Oetw37GMGVoUU5g