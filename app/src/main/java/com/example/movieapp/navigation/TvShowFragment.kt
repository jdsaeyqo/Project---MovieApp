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
import com.example.movieapp.databinding.FragTvshowBinding
import com.example.movieapp.model.tvshow.TvShow
import com.example.movieapp.model.tvshow.TvShowAdapter
import com.example.movieapp.model.tvshow.TvShowRepository

class TvShowFragment : Fragment() {

    private var _binding: FragTvshowBinding? = null
    private val binding get() = _binding!!

    private var populartvshowPage = 1
    private var topRatedtvshowPage = 1
    private var onAirtvshowPage = 1



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragTvshowBinding.inflate(inflater, container, false)

        initRecyclerView()

        getPopularTvShows()
        getTopRatedTvShows()
        getOnAirTvShows()


        return binding.root

    }

    private fun initRecyclerView() = with(binding) {

        recyclePopularTvshow.apply {
            adapter = TvShowAdapter(mutableListOf()) { tvshow ->
                showTvShowDetails(tvshow)
            }
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        recycleOnairTvshow.apply {
            adapter = TvShowAdapter(mutableListOf()) { tvshow ->
                showTvShowDetails(tvshow)
            }
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
        recycleTopRatedTvshow.apply {
            adapter = TvShowAdapter(mutableListOf()) { tvshow ->
                showTvShowDetails(tvshow)
            }
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun showTvShowDetails(tvshow: TvShow) {
        val intent = Intent(requireActivity(), TvShowDetail::class.java)
        intent.putExtra(TVSHOW_BACKDROP, tvshow.backdropPath)
        intent.putExtra(TVSHOW_POSTER, tvshow.posterPath)
        intent.putExtra(TVSHOW_NAME, tvshow.name)
        intent.putExtra(TVSHOW_RATING, tvshow.rating)
        intent.putExtra(TVSHOW_FIRSTAIRDATE, tvshow.first_air_date)
        intent.putExtra(TVSHOW_OVERVIEW, tvshow.overview)
        intent.putExtra(TVSHOW_ID, tvshow.id)

        startActivity(intent)
    }


    private fun getPopularTvShows() {
        TvShowRepository.getPopularTvShows(
            populartvshowPage,
            ::onPopularTvShowsFetched,
            ::onError
        )
    }

    private fun getTopRatedTvShows() {
        TvShowRepository.getTopRatedTvShow(
            topRatedtvshowPage,
            ::onTopRatedTvShowsFetched,
            ::onError
        )

    }

    private fun getOnAirTvShows() {
        TvShowRepository.getOnAirTvShow(
            onAirtvshowPage,
            ::OnAirTvShowsFetched,
            ::onError
        )

    }

    private fun attachPopularTvShowsOnScrollListener() {
        binding.recyclePopularTvshow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = binding.recyclePopularTvshow.layoutManager?.itemCount
                val visibleItemCount = binding.recyclePopularTvshow.layoutManager?.childCount
                val firstVisibleItem =
                    (binding.recyclePopularTvshow.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()


                if (firstVisibleItem + visibleItemCount!! == totalItemCount) {
                    recyclerView.removeOnScrollListener(this)
                    populartvshowPage++
                    getPopularTvShows()
                }
            }
        })
    }


    private fun attachTopRatedTvShowsOnScrollListener() {
        binding.recycleTopRatedTvshow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = binding.recycleTopRatedTvshow.layoutManager?.itemCount
                val visibleItemCount = binding.recycleTopRatedTvshow.layoutManager?.childCount
                val firstVisibleItem =
                    (binding.recycleTopRatedTvshow.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount!! == totalItemCount) {
                    recyclerView.removeOnScrollListener(this)
                    topRatedtvshowPage++
                    getTopRatedTvShows()
                }
            }
        })
    }

    private fun attachOnAirTvShowsOnScrollListener() {
        binding.recycleOnairTvshow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = binding.recycleOnairTvshow.layoutManager?.itemCount
                val visibleItemCount = binding.recycleOnairTvshow.layoutManager?.childCount
                val firstVisibleItem =
                    (binding.recycleOnairTvshow.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount!! == totalItemCount) {
                    recyclerView.removeOnScrollListener(this)
                    onAirtvshowPage++
                    getOnAirTvShows()
                }
            }
        })


    }

    private fun onPopularTvShowsFetched(tvshow: MutableList<TvShow>) {
        (binding.recyclePopularTvshow.adapter as TvShowAdapter).appendTvShows(tvshow)
        attachPopularTvShowsOnScrollListener()
    }

    private fun onTopRatedTvShowsFetched(tvshow: MutableList<TvShow>) {
        (binding.recycleTopRatedTvshow.adapter as TvShowAdapter).appendTvShows(tvshow)
        attachTopRatedTvShowsOnScrollListener()
    }

    private fun OnAirTvShowsFetched(tvshow: MutableList<TvShow>) {
        (binding.recycleOnairTvshow.adapter as TvShowAdapter).appendTvShows(tvshow)
        attachOnAirTvShowsOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(activity, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

}