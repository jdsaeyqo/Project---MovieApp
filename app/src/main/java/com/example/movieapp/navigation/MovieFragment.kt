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
import com.example.movieapp.databinding.FragMovieBinding
import com.example.movieapp.model.movie.Movie
import com.example.movieapp.model.movie.MovieAdapter
import com.example.movieapp.model.movie.MovieRepository

class MovieFragment : Fragment() {

    private var _binding: FragMovieBinding? = null
    private val binding get() = _binding!!

    private var popularMoviesPage = 1
    private var topRatedMoviesPage = 1
    private var upComingMoviesPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragMovieBinding.inflate(inflater, container, false)

        initRecyclerView()

        getPopularMovies()
        getTopRatedMovies()
        getUpcomingMovies()

        return binding.root

    }

    private fun initRecyclerView() = with(binding) {

        recyclePopular.apply {
            adapter = MovieAdapter(mutableListOf()) { movie ->
                showMovieDetails(movie)
            }
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        recycleTopRated.apply {
            adapter = MovieAdapter(mutableListOf()) { movie ->
                showMovieDetails(movie)
            }
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        recycleUpcoming.apply {
            adapter = MovieAdapter(mutableListOf()) { movie ->
                showMovieDetails(movie)
            }
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(requireActivity(), MovieDetail::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        intent.putExtra(MOVIE_ID, movie.id)

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
        binding.recyclePopular.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = binding.recyclePopular.layoutManager?.itemCount
                val visibleItemCount = binding.recyclePopular.layoutManager?.childCount
                val firstVisibleItem =
                    (binding.recyclePopular.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount!! == totalItemCount) {
                    recyclerView.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }


    private fun attachTopRatedMoviesOnScrollListener() {
        binding.recycleTopRated.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = binding.recycleTopRated.layoutManager?.itemCount
                val visibleItemCount = binding.recycleTopRated.layoutManager?.childCount
                val firstVisibleItem =
                    (binding.recycleTopRated.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()


                if (firstVisibleItem + visibleItemCount!! == totalItemCount) {
                    recyclerView.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()
                }
            }
        })
    }

    private fun attachUpComingMoviesOnScrollListener() {
        binding.recycleUpcoming.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = binding.recycleUpcoming.layoutManager?.itemCount
                val visibleItemCount = binding.recycleUpcoming.layoutManager?.childCount
                val firstVisibleItem =
                    (binding.recycleUpcoming.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()


                if (firstVisibleItem + visibleItemCount!! == totalItemCount) {
                    recyclerView.removeOnScrollListener(this)
                    upComingMoviesPage++
                    getUpcomingMovies()
                }
            }
        })
    }


    private fun onPopularMoviesFetched(movies: MutableList<Movie>) {
        (binding.recyclePopular.adapter as MovieAdapter).appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }

    private fun onTopRatedMoviesFetched(movies: MutableList<Movie>) {
        (binding.recycleTopRated.adapter as MovieAdapter).appendMovies(movies)
        attachTopRatedMoviesOnScrollListener()
    }

    private fun onUpComingMoviesFetched(movies: MutableList<Movie>) {
        (binding.recycleUpcoming.adapter as MovieAdapter).appendMovies(movies)
        attachUpComingMoviesOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(activity, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}