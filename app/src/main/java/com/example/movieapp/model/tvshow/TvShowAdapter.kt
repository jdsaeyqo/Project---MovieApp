package com.example.movieapp.model.tvshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movieapp.R

class TvShowAdapter(
    var tvshows : MutableList<TvShow>, private var onTvshowClick : (tvshow: TvShow)->Unit

):RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowAdapter.TvShowViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tvshow,parent,false)

        return TvShowViewHolder(view)
    }


    override fun getItemCount(): Int {
        return  tvshows.size
    }

    override fun onBindViewHolder(holder: TvShowAdapter.TvShowViewHolder, position: Int) {
        holder.bind(tvshows[position])
    }

    fun appendTvShows(tvshows : List<TvShow>){
        this.tvshows.addAll(tvshows)
        notifyItemRangeInserted(this.tvshows.size,tvshows.size -1)
    }

    inner class TvShowViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val poster : ImageView = itemView.findViewById(R.id.item_tvshow_poster)

        fun bind(tvshow : TvShow){
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${tvshow.posterPath}")
                .transform(CenterCrop())
                .into(poster)

            itemView.setOnClickListener { onTvshowClick.invoke(tvshow) }
        }
    }

}