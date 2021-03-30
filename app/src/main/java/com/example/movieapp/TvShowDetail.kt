package com.example.movieapp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movieapp.model.tvshow.TvShowRepository
import com.example.movieapp.model.tvshow.Video
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_tvshow_detail.*


const val TVSHOW_BACKDROP = "extra_tvshow_backdrop"
const val TVSHOW_POSTER = "extra_tvshow_poster"
const val TVSHOW_NAME = "extra_tvshow_name"
const val TVSHOW_RATING = "extra_tvshow_rating"
const val TVSHOW_FIRSTAIRDATE = "extra_tvshow_first_air_date"
const val TVSHOW_OVERVIEW = "extra_tvshow_overview"
const val TVSHOW_ID = "extra_tvshow_id"

class TvShowDetail : YouTubeBaseActivity(),YouTubePlayer.OnInitializedListener {


    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var name: TextView
    private lateinit var rating: RatingBar
    private lateinit var firstairdate: TextView
    private lateinit var overview: TextView
    private lateinit var VIDEO_ID : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tvshow_detail)

        backdrop = findViewById(R.id.tvshow_backdrop)
        poster = findViewById(R.id.tvshow_poster)
        name = findViewById(R.id.tvshow_title)
        rating = findViewById(R.id.tvshow_rating)
        firstairdate = findViewById(R.id.tvshow_firstair_date)
        overview = findViewById(R.id.tvshow_overview)

        val extras = intent.extras

       if(extras != null){
            populateDetails(extras)
        }else{
            finish()
        }


    }


    private fun populateDetails(extras: Bundle) {
        extras.getString(TVSHOW_BACKDROP)?.let {backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)

        }
        extras.getString(TVSHOW_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }
        name.text = extras.getString(TVSHOW_NAME, "")
        rating.rating = extras.getFloat(TVSHOW_RATING, 0f) / 2
        firstairdate.text = extras.getString(TVSHOW_FIRSTAIRDATE, "")
        overview.text = extras.getString(TVSHOW_OVERVIEW, "")
        var TVSHOW_ID = extras.getLong(TVSHOW_ID)
        TvShowRepository.getVideo(TVSHOW_ID, onSuccess = ::onVideosFetched, onError = ::onError)
    }

    private fun onVideosFetched(video: List<Video>) {
        VIDEO_ID = video[0].video_key
        tvshow_backdrop.visibility = View.GONE

        yt_tvshow_player.initialize("AIzaSyD6krj8VZgysv89-hLmGKSa8s_haSYcxAg",this)

    }
    private fun onError(){
        yt_tvshow_player.visibility = View.GONE
    }


    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        if(!p2){
            p1?.cueVideo(VIDEO_ID)
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        if(p1!!.isUserRecoverableError){
            p1.getErrorDialog(this,1).show()
        }else{
            Toast.makeText(this,"초기화 실패"+p1.toString(), Toast.LENGTH_LONG).show()
        }
    }
}