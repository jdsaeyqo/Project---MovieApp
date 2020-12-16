package com.example.movieapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey :String = "ee2b86b7b047fd828cc1c8353abb6114" ,
        @Query("page") page : Int

    ) : Call<GetMoviesResponse>
}