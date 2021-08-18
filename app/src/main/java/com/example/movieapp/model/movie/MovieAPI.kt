package com.example.movieapp.model.movie


import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey :String = "ee2b86b7b047fd828cc1c8353abb6114" ,
        @Query("page") page : Int,
        @Query("language") language : String = "ko"

    ) : Response<GetMoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey :String = "ee2b86b7b047fd828cc1c8353abb6114" ,
        @Query("page") page : Int,
        @Query("language") language : String = "ko"

    ): Response<GetMoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey :String = "ee2b86b7b047fd828cc1c8353abb6114" ,
        @Query("page") page : Int,
        @Query("language") language : String = "ko"

    ): Response<GetMoviesResponse>


}

interface VideoAPI{
    @GET("movie/{movie_id}/videos")
    suspend fun getVideoTrailer(
        @Path("movie_id") movieID: Long, //선택된 movie id
        @Query("api_key") apiKey: String = "ee2b86b7b047fd828cc1c8353abb6114",
        @Query("language") language : String = "en"
    ): Response<GetVideoResponse>
}