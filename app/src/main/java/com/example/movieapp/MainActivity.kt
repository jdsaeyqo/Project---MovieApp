package com.example.movieapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.model.Movie
import com.example.movieapp.model.MovieRepository

class MainActivity : AppCompatActivity() {


    private lateinit var popular_recycler: RecyclerView
    private lateinit var popularmovieadapter: MovieAdapter
    lateinit var popularMoviesLayoutMgr: LinearLayoutManager
    private var popularMoviesPage = 1

    private lateinit var topRated_recycler: RecyclerView
    private lateinit var topRatedMoviesAdapter: MovieAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager
    private var topRatedMoviesPage = 1

    private lateinit var upComing_recycler: RecyclerView
    private lateinit var upComingMoviesAdapter: MovieAdapter
    private lateinit var upComingMoviesLayoutMgr: LinearLayoutManager
    private var upComingMoviesPage = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        popularMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        popular_recycler = findViewById(R.id.recycle_popular)
        popular_recycler.layoutManager = popularMoviesLayoutMgr
        popularmovieadapter = MovieAdapter(mutableListOf()){ movie -> showMovieDetails(movie)}
        popular_recycler.adapter = popularmovieadapter

        topRatedMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        topRated_recycler = findViewById(R.id.recycle_top_rated)
        topRated_recycler.layoutManager = topRatedMoviesLayoutMgr
        topRatedMoviesAdapter = MovieAdapter(mutableListOf()){ movie -> showMovieDetails(movie)}
        topRated_recycler.adapter = topRatedMoviesAdapter

        upComingMoviesLayoutMgr = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        upComing_recycler = findViewById(R.id.recycle_upcoming)
        upComing_recycler.layoutManager = upComingMoviesLayoutMgr
        upComingMoviesAdapter = MovieAdapter(mutableListOf()){ movie -> showMovieDetails(movie)}
        upComing_recycler.adapter = upComingMoviesAdapter

        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()


    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetail::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        intent.putExtra(MOVIE_ID,movie.id)

        startActivity(intent)
    }

    private fun getUpcomingMovies() {
        MovieRepository.getUpcomingMovies(
            upComingMoviesPage,
            ::onUpComingMoviesFetched,
            ::onError
        )

    }


    private fun getTopRatedMovies() {
        MovieRepository.getTopRatedMovies(
            topRatedMoviesPage,
            ::onTopRatedMoviesFetched,
            ::onError
        )

    }

    private fun getPopularMovies() {
        MovieRepository.getPopularMovies(
            popularMoviesPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }

    private fun attachPopularMoviesOnScrollListener() {
        popular_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutMgr.itemCount
                val visibleItemCount = popularMoviesLayoutMgr.childCount
                val firstVisibleItem = popularMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    recyclerView.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }


    private fun attachTopRatedMoviesOnScrollListener() {
        topRated_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedMoviesLayoutMgr.itemCount
                val visibleItemCount = topRatedMoviesLayoutMgr.childCount
                val firstVisibleItem = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    recyclerView.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()
                }
            }
        })
    }

    private fun attachUpComingMoviesOnScrollListener() {
        upComing_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upComingMoviesLayoutMgr.itemCount
                val visibleItemCount = upComingMoviesLayoutMgr.childCount
                val firstVisibleItem = upComingMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    recyclerView.removeOnScrollListener(this)
                    upComingMoviesPage++
                    getUpcomingMovies()
                }
            }
        })
    }


    private fun onPopularMoviesFetched(movies: MutableList<Movie>) {
        popularmovieadapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }

    private fun onTopRatedMoviesFetched(movies: MutableList<Movie>) {
        topRatedMoviesAdapter.appendMovies(movies)
        attachTopRatedMoviesOnScrollListener()
    }

    private fun onUpComingMoviesFetched(movies: MutableList<Movie>) {
        upComingMoviesAdapter.appendMovies(movies)
        attachUpComingMoviesOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

}
