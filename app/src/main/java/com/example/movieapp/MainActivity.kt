package com.example.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.MovieRepository.getPopularMovies
import com.example.movieapp.model.GetMoviesResponse
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerview1: RecyclerView
    private lateinit var movieadapter: MovieAdapter
    lateinit var popularMoviesLayoutMgr: LinearLayoutManager
     var popularMoviePage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        popularMoviesLayoutMgr= LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)


        recyclerview1 = findViewById(R.id.recycle_popular)
        recyclerview1.layoutManager =popularMoviesLayoutMgr
        movieadapter = MovieAdapter(mutableListOf())
        recyclerview1.adapter = movieadapter


        getPopularMovies()

    }

    private fun getPopularMovies() {
        MovieRepository.getPopularMovies(
            popularMoviePage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    private fun onPopularMoviesFetched(movies: MutableList<Movie>) {
        movieadapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }

    private fun attachPopularMoviesOnScrollListener() {
        recyclerview1.addOnScrollListener(object  : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                val visibleItemCount = popularMoviesLayoutMgr.childCount
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    recyclerView.removeOnScrollListener(this)
                    popularMoviePage++
                    getPopularMovies()
                }
            }
        })
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }
}
