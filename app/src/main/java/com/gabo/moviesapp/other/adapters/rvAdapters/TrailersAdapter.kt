package com.gabo.moviesapp.other.adapters.rvAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.gabo.moviesapp.data.models.movieTrailerModels.MovieTrailerModel
import com.gabo.moviesapp.databinding.TrailerItemBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class TrailersAdapter(private val lifecycle: Lifecycle) : RecyclerView.Adapter<TrailersAdapter.TrailersVH>() {
    private var list: List<MovieTrailerModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<MovieTrailerModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class TrailersVH(private val binding: TrailerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MovieTrailerModel) {
            binding.tvTrailerName.text = model.name
            val youTubePlayerView: YouTubePlayerView = binding.youtubeView
            lifecycle.addObserver(youTubePlayerView)

            youTubePlayerView.addYouTubePlayerListener(object :
                AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = model.key
                    youTubePlayer.loadVideo(videoId, 0f)
                    youTubePlayer.pause()
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailersVH {
        val binding = TrailerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrailersVH(binding)
    }

    override fun onBindViewHolder(holder: TrailersVH, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}