package com.example.movieapp.model

import com.google.gson.annotations.SerializedName

data class Movie (
    @SerializedName("id") val id : Long,
    @SerializedName("title") val title : String,
    @SerializedName("overview") val overview : String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("release_date") val releaseDate: String
)
data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: MutableList<Movie>,
    @SerializedName("total_pages") val pages: Int
)

data class Video(
    @SerializedName("id") val video_id : String, //@SerialzedName 은 변수명을 다르게 하고 싶을때 사용
    @SerializedName("key") val video_key : String,
    @SerializedName("name") val video_name : String,
    @SerializedName("size") val video_size : Int
)


data class GetVideoResponse(
    @SerializedName("id") val id : Int,
    @SerializedName("results") val videos: List<Video>
)