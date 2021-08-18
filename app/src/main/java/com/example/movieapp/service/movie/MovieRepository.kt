package com.example.movieapp.service.movie

import com.example.movieapp.model.movie.Movie
import com.example.movieapp.model.movie.Video
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
        if (body != null) {
            onSuccess.invoke(body.movies)
        } else {
            onError.invoke()
        }
    }

    suspend fun getTopRatedMovies(
        page: Int = 1,
        onSuccess: (movies: MutableList<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        val body = api_movie?.getTopRatedMovies(page = page)!!.body()
        if (body != null) {
            onSuccess.invoke(body.movies)
        } else {
            onError.invoke()
        }

    }

    suspend fun getUpcomingMovies(
        page: Int = 1,
        onSuccess: (movies: MutableList<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        val body = api_movie?.getUpcomingMovies(page = page)!!.body()
        if (body != null) {
            onSuccess.invoke(body.movies)
        } else {
            onError.invoke()
        }

    }

    suspend fun getVideo(
        movie_id: Long,
        onSuccess: (videos: List<Video>) -> Unit,
        onError: () -> Unit
    ) {

        val body = api_video?.getVideoTrailer(movieID = movie_id)!!.body()
        if (body!!.videos.isNotEmpty()) {
            onSuccess.invoke(body.videos)
        } else {
            onError.invoke()
        }

    }


}