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
import com.example.movieapp.model.tvshow.TvShow
import com.example.movieapp.model.tvshow.TvShowAdapter
import com.example.movieapp.model.tvshow.TvShowRepository
import kotlinx.android.synthetic.main.frag_tvshow.view.*

class TvShowFragment: Fragment() {

    private lateinit var popular_recycler: RecyclerView
    private lateinit var populartvshowadapter: TvShowAdapter
    lateinit var populartvshowLayoutMgr: LinearLayoutManager
    private var populartvshowPage = 1

    private lateinit var topRated_recycler: RecyclerView
    private lateinit var topRatedtvshowAdapter: TvShowAdapter
    private lateinit var topRatedtvshowLayoutMgr: LinearLayoutManager
    private var topRatedtvshowPage = 1

    private lateinit var onAir_recycler: RecyclerView
    private lateinit var onAirtvshowAdapter: TvShowAdapter
    private lateinit var onAirtvshowLayoutMgr: LinearLayoutManager
    private var onAirtvshowPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = LayoutInflater.from(activity).inflate(R.layout.frag_tvshow, container, false)

        popular_recycler = view.recycle_popular_tvshow
        populartvshowLayoutMgr =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        populartvshowadapter = TvShowAdapter(mutableListOf()) { tvshow ->
            showTvShowDetails(tvshow)
        }
        popular_recycler.layoutManager = populartvshowLayoutMgr
        popular_recycler.adapter = populartvshowadapter



        topRated_recycler = view.recycle_top_rated_tvshow
        topRatedtvshowLayoutMgr =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        topRatedtvshowAdapter = TvShowAdapter(mutableListOf()) { tvshow ->
            showTvShowDetails(tvshow)
        }
        topRated_recycler.layoutManager = topRatedtvshowLayoutMgr
        topRated_recycler.adapter = topRatedtvshowAdapter



        onAir_recycler = view.recycle_onair_tvshow
        onAirtvshowLayoutMgr = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        onAirtvshowAdapter = TvShowAdapter(mutableListOf()){tvshow ->
            showTvShowDetails(tvshow)
        }
        onAir_recycler.layoutManager = onAirtvshowLayoutMgr
        onAir_recycler.adapter = onAirtvshowAdapter

        getPopularTvShows()
        getTopRatedTvShows()
        getOnAirTvShows()


        return view

    }

    private fun showTvShowDetails(tvshow: TvShow) {
        val intent = Intent(activity!!, TvShowDetail::class.java)
        intent.putExtra(TVSHOW_BACKDROP, tvshow.backdropPath)
        intent.putExtra(TVSHOW_POSTER, tvshow.posterPath)
        intent.putExtra(TVSHOW_NAME, tvshow.name)
        intent.putExtra(TVSHOW_RATING, tvshow.rating)
        intent.putExtra(TVSHOW_FIRSTAIRDATE, tvshow.first_air_date)
        intent.putExtra(TVSHOW_OVERVIEW, tvshow.overview)
        intent.putExtra(TVSHOW_ID,tvshow.id)

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
    popular_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val totalItemCount = populartvshowLayoutMgr.itemCount
            val visibleItemCount = populartvshowLayoutMgr.childCount
            val firstVisibleItem = populartvshowLayoutMgr.findFirstVisibleItemPosition()

            if (firstVisibleItem + visibleItemCount == totalItemCount) {
                recyclerView.removeOnScrollListener(this)
                populartvshowPage++
                getPopularTvShows()
            }
        }
    })
}


private fun attachTopRatedTvShowsOnScrollListener() {
    topRated_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val totalItemCount = topRatedtvshowLayoutMgr.itemCount
            val visibleItemCount = topRatedtvshowLayoutMgr.childCount
            val firstVisibleItem = topRatedtvshowLayoutMgr.findFirstVisibleItemPosition()

            if (firstVisibleItem + visibleItemCount == totalItemCount) {
                recyclerView.removeOnScrollListener(this)
                topRatedtvshowPage++
                getTopRatedTvShows()
            }
        }
    })
}

private fun attachOnAirTvShowsOnScrollListener() {
    onAir_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val totalItemCount = onAirtvshowLayoutMgr.itemCount
            val visibleItemCount = onAirtvshowLayoutMgr.childCount
            val firstVisibleItem = onAirtvshowLayoutMgr.findFirstVisibleItemPosition()

            if (firstVisibleItem + visibleItemCount == totalItemCount) {
                recyclerView.removeOnScrollListener(this)
                onAirtvshowPage++
                getOnAirTvShows()
            }
        }
    })


}
    private fun onPopularTvShowsFetched(tvshow: MutableList<TvShow>) {
        populartvshowadapter.appendTvShows(tvshow)
        attachPopularTvShowsOnScrollListener()
    }

    private fun onTopRatedTvShowsFetched(tvshow: MutableList<TvShow>) {
        topRatedtvshowAdapter.appendTvShows(tvshow)
        attachTopRatedTvShowsOnScrollListener()
    }

    private fun OnAirTvShowsFetched(tvshow: MutableList<TvShow>) {
        onAirtvshowAdapter.appendTvShows(tvshow)
        attachOnAirTvShowsOnScrollListener()
    }

    private fun onError() {
        Toast.makeText(activity, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }

}