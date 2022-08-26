package com.gabo.moviesapp.other.adapters.rvAdapters

import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.NowStreamingMovieItemBinding
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gabo.moviesapp.other.common.BASE_IMAGE_URL
import com.gabo.moviesapp.other.common.setNowStreamingData

class NowStreamingMoviesAdapter(private val click: (MovieModel) -> Unit): RecyclerView.Adapter<NowStreamingMoviesAdapter.NowStreamingMoviesVH>() {
    private var list: List<MovieModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<MovieModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class NowStreamingMoviesVH(private val binding: NowStreamingMovieItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MovieModel, click: (MovieModel) -> Unit){
           binding.setNowStreamingData(model,click)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowStreamingMoviesVH {
        val binding = NowStreamingMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NowStreamingMoviesVH(binding)
    }

    override fun onBindViewHolder(holder: NowStreamingMoviesVH, position: Int) {
        val item = list[position]
        holder.bind(item, click)
    }

    override fun getItemCount(): Int = list.size
}