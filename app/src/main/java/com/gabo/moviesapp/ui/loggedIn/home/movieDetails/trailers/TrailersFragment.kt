package com.gabo.moviesapp.ui.loggedIn.home.movieDetails.trailers

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.moviesapp.R
import com.gabo.moviesapp.data.models.movieTrailerModels.MovieTrailersModel
import com.gabo.moviesapp.databinding.FragmentTrailersBinding
import com.gabo.moviesapp.other.adapters.rvAdapters.TrailersAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.other.common.setupAdapter
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.ui.MainViewModel
import com.gabo.moviesapp.ui.loggedIn.home.movieDetails.MovieDetailsFragment
import com.gabo.moviesapp.ui.loggedIn.home.movieDetails.MovieDetailsFragmentArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class TrailersFragment : BaseFragment<TrailersViewModel, FragmentTrailersBinding>(
    TrailersViewModel::class, FragmentTrailersBinding::inflate
) {
    private val activityViewModel: MainViewModel by activityViewModels()
    private lateinit var trailersAdapter: TrailersAdapter
    override fun setupView(savedInstanceState: Bundle?) {
        trailersAdapter =
            binding.root.setupAdapter(
                TrailersAdapter(viewLifecycleOwner.lifecycle),
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            )
        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            activityViewModel.movieDetailsFragmentArgs?.collect {
                it.let { id ->
                    viewModel.getTrailers(id).collect {
                        when (it) {
                            is ResponseHandler.Success<MovieTrailersModel> -> {
//                                val youTubePlayerView: YouTubePlayerView = binding.youtubeView
//                                lifecycle.addObserver(youTubePlayerView)
//
//                                youTubePlayerView.addYouTubePlayerListener(object :
//                                    AbstractYouTubePlayerListener() {
//                                    override fun onReady(youTubePlayer: YouTubePlayer) {
//                                val videoId = it.data!!.results[0].key
                                trailersAdapter.submitList(it.data?.results!!)
//                                        youTubePlayer.loadVideo(videoId, 0f)
//                                        youTubePlayer.pause()
//                                    }
//                                })
                            }
                            else -> {}
                        }

                    }
                }
            }
        }
    }
}