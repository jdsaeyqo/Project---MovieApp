package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movieapp.databinding.ItemTvshowBinding
import com.example.movieapp.model.tvshow.TvShow

class TvShowAdapter(
    var tvshows: MutableList<TvShow>, private var onTvshowClick: (tvshow: TvShow) -> Unit

) : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowViewHolder {

        val view = ItemTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(view)
    }


    override fun getItemCount(): Int {
        return tvshows.size
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        holder.bind(tvshows[position])
    }

    fun appendTvShows(tvshows: List<TvShow>) {
        this.tvshows.addAll(tvshows)
        notifyItemRangeInserted(this.tvshows.size, tvshows.size - 1)
    }

    inner class TvShowViewHolder(private val binding: ItemTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tvshow: TvShow) = with(binding) {
            Glide.with(root)
                .load("https://image.tmdb.org/t/p/w342${tvshow.posterPath}")
                .transform(CenterCrop())
                .into(binding.itemTvshowPoster)

            root.setOnClickListener { onTvshowClick.invoke(tvshow) }
        }
    }

}