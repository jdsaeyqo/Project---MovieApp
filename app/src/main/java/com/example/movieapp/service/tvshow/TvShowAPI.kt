package com.example.movieapp.service.tvshow



import com.example.movieapp.model.tvshow.GetTvShowResponse
import com.example.movieapp.model.tvshow.GetVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowAPI {
    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey :String = "ee2b86b7b047fd828cc1c8353abb6114",
        @Query("page") page : Int,
        @Query("language") language : String = "ko"

    ) : Response<GetTvShowResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShows(
        @Query("api_key") apiKey :String = "ee2b86b7b047fd828cc1c8353abb6114",
        @Query("page") page : Int,
        @Query("language") language : String = "ko"

    ): Response<GetTvShowResponse>

    @GET("tv/on_the_air")
    suspend fun getOnAirTvShows(
        @Query("api_key") apiKey :String = "ee2b86b7b047fd828cc1c8353abb6114",
        @Query("page") page : Int,
        @Query("language") language : String = "ko"

    ): Response<GetTvShowResponse>
}

interface VideoAPI{
    @GET("tv/{tv_id}/videos")
    suspend fun getVideoTrailer(
        @Path("tv_id") TvShowID: Long, //선택된 tv id
        @Query("api_key") apiKey: String = "ee2b86b7b047fd828cc1c8353abb6114",
        @Query("language") language : String = "en"
    ): Response<GetVideoResponse>
}