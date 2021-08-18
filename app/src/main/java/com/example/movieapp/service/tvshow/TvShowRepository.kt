package com.example.movieapp.service.tvshow


import com.example.movieapp.model.tvshow.TvShow
import com.example.movieapp.model.tvshow.Video
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

    suspend fun getPopularTvShows(
        page: Int = 1,
        onSuccess: (tvshows: MutableList<TvShow>) -> Unit,
        onError: () -> Unit
    ) {
        val body = api_tvshow?.getPopularTvShows(page = page)!!.body()
        if(body != null){
            onSuccess.invoke(body.tvshows)
        }else{
            onError.invoke()
        }
    }

    suspend fun getTopRatedTvShow(
        page: Int = 1,
        onSuccess: (tvshows: MutableList<TvShow>) -> Unit,
        onError: () -> Unit
    ) {
       val body =  api_tvshow?.getTopRatedTvShows(page = page)!!.body()
        if(body != null){
            onSuccess.invoke(body.tvshows)
        }else{
            onError.invoke()
        }

    }

    suspend fun getOnAirTvShow(
        page: Int = 1,
        onSuccess: (tvshows: MutableList<TvShow>) -> Unit,
        onError: () -> Unit
    ) {
       val body =  api_tvshow?.getOnAirTvShows(page = page)!!.body()
        if(body != null){
            onSuccess.invoke(body.tvshows)
        }else{
            onError.invoke()
        }
    }

    suspend fun getVideo(
        tvshow_id : Long,
        onSuccess: (videos : List<Video>) -> Unit,
        onError: () -> Unit
    ) {

        val body = api_video?.getVideoTrailer(TvShowID = tvshow_id)!!.body()
        if(body!!.videos.isNotEmpty()){
            onSuccess.invoke(body.videos)
        }else{
            onError.invoke()
        }
    }

}