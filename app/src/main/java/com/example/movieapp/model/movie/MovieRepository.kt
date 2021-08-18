package com.example.movieapp.model.movie

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRepository {

    private var api_movie: MovieAPI? = null
    var api_video: VideoAPI? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api_movie = retrofit.create(MovieAPI::class.java)
        api_video = retrofit.create(VideoAPI::class.java)
    }

    suspend fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movies: MutableList<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        val body = api_movie?.getPopularMovies(page = page)!!.body()
        if(body!=null){
            onSuccess.invoke(body.movies)
        }else{
            onError.invoke()
        }
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                    call: Call<GetMoviesResponse>,
//                    response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//
//                        if (responseBody != null) {
//                            onSuccess.invoke(responseBody.movies)
//                        } else {
//                            onError.invoke()
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    onError.invoke()
//                }
//            })
    }

    suspend fun getTopRatedMovies(
        page: Int = 1,
        onSuccess: (movies: MutableList<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        val body = api_movie?.getTopRatedMovies(page = page)!!.body()
        if(body!=null){
            onSuccess.invoke(body.movies)
        }else{
            onError.invoke()
        }
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                    call: Call<GetMoviesResponse>,
//                    response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//
//                        if (responseBody != null) {
//                            onSuccess.invoke(responseBody.movies)
//                        } else {
//                            onError.invoke()
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    onError.invoke()
//                }
//            })
    }

    suspend fun getUpcomingMovies(
        page: Int = 1,
        onSuccess: (movies: MutableList<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        val body = api_movie?.getUpcomingMovies(page = page)!!.body()
        if(body!=null){
            onSuccess.invoke(body.movies)
        }else{
            onError.invoke()
        }
//            .enqueue(object : Callback<GetMoviesResponse> {
//                override fun onResponse(
//                    call: Call<GetMoviesResponse>,
//                    response: Response<GetMoviesResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        val responseBody = response.body()
//
//                        if (responseBody != null) {
//                            onSuccess.invoke(responseBody.movies)
//                        } else {
//                            onError.invoke()
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
//                    onError.invoke()
//                }
//            })
    }

    suspend fun getVideo(
        movie_id : Long,
        onSuccess: (videos : List<Video>) -> Unit,
        onError: () -> Unit
    ) {

       val body =  api_video?.getVideoTrailer(movieID = movie_id)!!.body()
        if(body!!.videos.isNotEmpty()){
            onSuccess.invoke(body.videos)
        }else{
            onError.invoke()
        }
//            .enqueue(object : Callback<GetVideoResponse> {
//            override fun onResponse(call: Call<GetVideoResponse>, response: Response<GetVideoResponse>) {
//                if(response.isSuccessful) {
//                    val responseBody = response.body()
//                    Log.d("response", responseBody.toString())
//                    if (responseBody!!.videos.isNotEmpty() || responseBody.videos.isNotEmpty()){
//                        onSuccess.invoke(responseBody.videos)
//                    } else {
//                        onError.invoke()
//                    }
//                } else {
//                    Log.d("Result", "응답이 없습니다.")
//                }
//            }
//
//            override fun onFailure(call: Call<GetVideoResponse>, t: Throwable) {
//                Log.d("Error", "오류가 발생했습니다.")
//            }
//        })
    }


}