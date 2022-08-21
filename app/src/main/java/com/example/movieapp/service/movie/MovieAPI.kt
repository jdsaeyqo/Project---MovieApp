package com.example.movieapp.service.movie


import com.example.movieapp.model.movie.GetMoviesResponse
import com.example.movieapp.model.movie.GetVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey :String = "1619850e39c4aaaa53c03843c119e4af" ,
        @Query("page") page : Int,
        @Query("language") language : String = "ko"

    ) : Response<GetMoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey :String = "1619850e39c4aaaa53c03843c119e4af" ,
        @Query("page") page : Int,
        @Query("language") language : String = "ko"

    ): Response<GetMoviesResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey :String = "1619850e39c4aaaa53c03843c119e4af" ,
        @Query("page") page : Int,
        @Query("language") language : String = "ko"

    ): Response<GetMoviesResponse>


}

interface VideoAPI{
    @GET("movie/{movie_id}/videos")
    suspend fun getVideoTrailer(
        @Path("movie_id") movieID: Long, //선택된 movie id
        @Query("api_key") apiKey: String = "1619850e39c4aaaa53c03843c119e4af",
        @Query("language") language : String = "en"
    ): Response<GetVideoResponse>
}