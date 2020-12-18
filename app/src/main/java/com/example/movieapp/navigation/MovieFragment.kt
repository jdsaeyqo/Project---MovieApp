package com.example.movieapp.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.*
import com.example.movieapp.model.movie.Movie
import com.example.movieapp.model.movie.MovieAdapter
import com.example.movieapp.model.movie.MovieRepository
import kotlinx.android.synthetic.main.frag_movie.view.*

class MovieFragment :Fragment(){

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = LayoutInflater.from(activity).inflate(R.layout.frag_movie, container, false)

        popular_recycler=view.recycle_popular
        popularMoviesLayoutMgr = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        popularmovieadapter =
            MovieAdapter(mutableListOf()) { movie ->
                showMovieDetails(movie)
            }
        popular_recycler.layoutManager = popularMoviesLayoutMgr
        popular_recycler.adapter = popularmovieadapter

        topRated_recycler=view.recycle_top_rated
        topRatedMoviesLayoutMgr = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        topRatedMoviesAdapter =
            MovieAdapter(mutableListOf()) { movie ->
                showMovieDetails(movie)
            }
        topRated_recycler.layoutManager = topRatedMoviesLayoutMgr
        topRated_recycler.adapter = topRatedMoviesAdapter

        upComing_recycler=view.recycle_upcoming
        upComingMoviesLayoutMgr = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        upComingMoviesAdapter =
            MovieAdapter(mutableListOf()) { movie ->
                showMovieDetails(movie)
            }
        upComing_recycler.layoutManager = upComingMoviesLayoutMgr
        upComing_recycler.adapter = upComingMoviesAdapter

        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()

        return view

    }

        private fun showMovieDetails(movie: Movie) {
            val intent = Intent(activity!!, MovieDetail::class.java)
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
        Toast.makeText(activity, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }
}