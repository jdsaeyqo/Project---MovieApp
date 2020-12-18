package com.example.movieapp

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movieapp.model.movie.MovieRepository.getVideo
import com.example.movieapp.model.movie.Video
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import kotlinx.android.synthetic.main.activity_movie_detail.*

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"
const val MOVIE_ID = "extra_movie_id"


class MovieDetail : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    private lateinit var VIDEO_ID : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)

        val extras = intent.extras

        if(extras != null){
            populateDetails(extras)
        }else{
            finish()
        }
    }

    private fun populateDetails(extras: Bundle) {
        extras.getString(MOVIE_BACKDROP)?.let {backdropPath ->
             Glide.with(this)
                 .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                 .transform(CenterCrop())
                 .into(backdrop)

        }
        extras.getString(MOVIE_POSTER)?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }
        title.text = extras.getString(MOVIE_TITLE, "")
        rating.rating = extras.getFloat(MOVIE_RATING, 0f) / 2
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
        var MOVIE_ID = extras.getLong(MOVIE_ID)
        getVideo(MOVIE_ID,onSuccess = ::onVideosFetched, onError = ::onError)
    }

    private fun onVideosFetched(video:List<Video>){
        VIDEO_ID = video[0].video_key
        movie_backdrop.visibility = View.GONE
        yt_player.initialize("AIzaSyDv9TGwK-FdSNMxp0u_Fh2FSekpk8Ukwlg",this)


    }

    private fun onError(){
        yt_player.visibility = View.GONE
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
            Toast.makeText(this,"초기화 실패"+p1.toString(),Toast.LENGTH_LONG).show()
        }
    }
}