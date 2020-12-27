package com.example.movieapp.model.tvshow


import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TvShowRepository {

    private var api_tvshow: TvShowAPI? = null
    var api_video: VideoAPI? = null

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api_tvshow = retrofit.create(TvShowAPI::class.java)
        api_video = retrofit.create(VideoAPI::class.java)
    }

    fun getPopularTvShows(
        page: Int = 1,
        onSuccess: (tvshows: MutableList<TvShow>) -> Unit,
        onError: () -> Unit
    ) {
        api_tvshow?.getPopularTvShows(page = page)!!
            .enqueue(object :Callback<GetTvShowResponse> {
                override fun onResponse(
                    call: Call<GetTvShowResponse>,
                    response: Response<GetTvShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.tvshows)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<GetTvShowResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
    fun getTopRatedTvShow(
        page: Int = 1,
        onSuccess: (tvshows: MutableList<TvShow>) -> Unit,
        onError: () -> Unit
    ) {
        api_tvshow?.getTopRatedTvShows(page = page)!!
            .enqueue(object : Callback<GetTvShowResponse> {
                override fun onResponse(
                    call: Call<GetTvShowResponse>,
                    response: Response<GetTvShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.tvshows)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<GetTvShowResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
    fun getOnAirTvShow(
        page: Int = 1,
        onSuccess: (tvshows: MutableList<TvShow>) -> Unit,
        onError: () -> Unit
    ) {
        api_tvshow?.getOnAirTvShows(page = page)!!
            .enqueue(object : Callback<GetTvShowResponse> {
                override fun onResponse(
                    call: Call<GetTvShowResponse>,
                    response: Response<GetTvShowResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.tvshows)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<GetTvShowResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getVideo(
        tvshow_id : Long,
        onSuccess: (videos : List<Video>) -> Unit,
        onError: () -> Unit
    ) {

        api_video?.getVideoTrailer(TvShowID = tvshow_id)!!
            .enqueue(object : Callback<GetVideoResponse> {
                override fun onResponse(call: Call<GetVideoResponse>, response: Response<GetVideoResponse>) {
                    if(response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("response", responseBody.toString())
                        if (responseBody!!.videos.isNotEmpty() || responseBody.videos.size > 0){
                            onSuccess.invoke(responseBody.videos)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        Log.d("Result", "응답이 없습니다.")
                    }
                }

                override fun onFailure(call: Call<GetVideoResponse>, t: Throwable) {
                    Log.d("Error", "오류가 발생했습니다.")
                }
            })
    }



}