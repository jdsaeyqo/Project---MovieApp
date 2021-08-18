package com.example.movieapp.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movieapp.databinding.ActivityTvshowDetailBinding
import com.example.movieapp.service.tvshow.TvShowRepository
import com.example.movieapp.model.tvshow.Video
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


const val TVSHOW_BACKDROP = "extra_tvshow_backdrop"
const val TVSHOW_POSTER = "extra_tvshow_poster"
const val TVSHOW_NAME = "extra_tvshow_name"
const val TVSHOW_RATING = "extra_tvshow_rating"
const val TVSHOW_FIRSTAIRDATE = "extra_tvshow_first_air_date"
const val TVSHOW_OVERVIEW = "extra_tvshow_overview"
const val TVSHOW_ID = "extra_tvshow_id"

class TvShowDetail : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    lateinit var binding: ActivityTvshowDetailBinding

    private lateinit var VIDEO_ID: String

    private val scope = CoroutineScope(Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvshowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras

        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }

    }

    private fun populateDetails(extras: Bundle) {
        extras.getString(TVSHOW_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(binding.tvshowBackdrop)

        }
        extras.getString(TVSHOW_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(binding.tvshowPoster)
        }
        binding.tvshowTitle.text = extras.getString(TVSHOW_NAME, "")
        binding.tvshowRating.rating = extras.getFloat(TVSHOW_RATING, 0f) / 2
        binding.tvshowFirstairDate.text = extras.getString(TVSHOW_FIRSTAIRDATE, "")
        binding.tvshowOverview.text = extras.getString(TVSHOW_OVERVIEW, "")
        val TVSHOW_ID = extras.getLong(TVSHOW_ID)

        scope.launch {
            TvShowRepository.getVideo(TVSHOW_ID, onSuccess = ::onVideosFetched, onError = ::onError)
        }

    }

    private fun onVideosFetched(video: List<Video>) {
        VIDEO_ID = video[0].video_key
        binding.tvshowBackdrop.visibility = View.GONE

        binding.ytTvshowPlayer.initialize("AIzaSyD6krj8VZgysv89-hLmGKSa8s_haSYcxAg", this)

    }

    private fun onError() {
        binding.ytTvshowPlayer.visibility = View.GONE
    }


    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if (!p2) {
            p1?.cueVideo(VIDEO_ID)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        if (p1!!.isUserRecoverableError) {
            p1.getErrorDialog(this, 1).show()
        } else {
            Toast.makeText(this, "초기화 실패$p1", Toast.LENGTH_LONG).show()
        }
    }
}