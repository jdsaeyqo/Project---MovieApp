package com.example.movieapp.model.tvshow


import com.google.gson.annotations.SerializedName

data class TvShow(
    @SerializedName("id") val id: Long,
    @SerializedName("number_of_seasons") val number_of_seasons : Long,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("first_air_date") val first_air_date: String
)

data class GetTvShowResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val tvshows: MutableList<TvShow>,
    @SerializedName("total_pages") val pages: Int
)

data class Video(
    @SerializedName("id") val video_id : String,
    @SerializedName("key") val video_key : String,
    @SerializedName("name") val video_name : String,
    @SerializedName("size") val video_size : Int
)


data class GetVideoResponse(
    @SerializedName("id") val id : Int,
    @SerializedName("results") val videos: List<Video>
)