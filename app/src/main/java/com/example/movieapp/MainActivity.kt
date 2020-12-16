package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.model.GetMoviesResponse
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var recyclerview: RecyclerView
    lateinit var movieadapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        recyclerview = findViewById(R.id.recycle_popular)
        recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        movieadapter = MovieAdapter(listOf())
        recyclerview.adapter = movieadapter

        MovieRepository.getPopularMovies(
            onSuccess = ::onPopularMoviesFetched,onError = ::onError
        )

    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        movieadapter.updateMovies(movies)
    }
    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }
}
