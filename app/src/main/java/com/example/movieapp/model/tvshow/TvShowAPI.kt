package com.example.movieapp.model.tvshow



import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowAPI {
    @GET("tv/popular")
    fun getPopularTvShows(
        @Query("api_key") apiKey :String = "ee2b86b7b047fd828cc1c8353abb6114",
        @Query("page") page : Int,
        @Query("language") language : String = "ko"

    ) : Call<GetTvShowResponse>

    @GET("tv/top_rated")
    fun getTopRatedTvShows(
        @Query("api_key") apiKey :String = "ee2b86b7b047fd828cc1c8353abb6114",
        @Query("page") page : Int,
        @Query("language") language : String = "ko"

    ): Call<GetTvShowResponse>

    @GET("tv/on_the_air")
    fun getOnAirTvShows(
        @Query("api_key") apiKey :String = "ee2b86b7b047fd828cc1c8353abb6114",
        @Query("page") page : Int,
        @Query("language") language : String = "ko"

    ): Call<GetTvShowResponse>
}

interface VideoAPI{
    @GET("tv/{tv_id}/videos")
    fun getVideoTrailer(
        @Path("tv_id") TvShowID: Long, //선택된 tv id
        @Query("api_key") apiKey: String = "ee2b86b7b047fd828cc1c8353abb6114",
        @Query("language") language : String = "en"
    ): Call<GetVideoResponse>
}