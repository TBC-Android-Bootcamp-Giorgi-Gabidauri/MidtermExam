package com.gabo.moviesapp.ui.loggedIn.home.movieDetails

import android.os.Bundle
import android.util.Log.d
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.moviesapp.data.models.genreModels.GenreModel
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.data.models.movieTrailerModels.MovieTrailersModel
import com.gabo.moviesapp.databinding.FragmentMovieDetailsBinding
import com.gabo.moviesapp.other.adapters.genresAdapter.GenresAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.SimilarMoviesAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.other.common.loadImage
import com.gabo.moviesapp.other.common.setupAdapter
import com.gabo.moviesapp.other.common.setupGenres
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import com.gabo.moviesapp.ui.MainActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MovieDetailsFragment : BaseFragment<MovieDetailsViewModel, FragmentMovieDetailsBinding>(
    MovieDetailsViewModel::class,
    FragmentMovieDetailsBinding::inflate
) {
    private val args: MovieDetailsFragmentArgs by navArgs()
    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter

    override fun setupView(savedInstanceState: Bundle?) {
        setupAdapters()
        setupObservers()
        setDetails()
        val genresList = (activity as MainActivity).genresList
        similarMoviesAdapter.submitGenresList(genresList)
    }

    private fun setupAdapters() {
        similarMoviesAdapter = binding.rvSimilarMovies.setupAdapter(SimilarMoviesAdapter {
            navigateToDetails(it)
        }, LinearLayoutManager(requireContext()))
    }

    private fun setDetails() {
        val movieModel = args.movieModel
        val genresToFilter = (activity as MainActivity).genresList
        with(binding) {
            ivPoster.loadImage(movieModel.backdropPath)
            tvDescriptionText.text = movieModel.overview
            tvTitle.text = movieModel.title
            tvRating.text = movieModel.Rating.toString()
            rvGenres.setupGenres(genresToFilter,movieModel)
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.getTrailers(args.movieModel.id).collect {
                when (it) {
                    is ResponseHandler.Success<MovieTrailersModel> -> {
                        val youTubePlayerView: YouTubePlayerView = binding.youtubeView
                        lifecycle.addObserver(youTubePlayerView)

                        youTubePlayerView.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                val videoId = it.data!!.results[0].key
                                youTubePlayer.loadVideo(videoId, 0f)
                            }
                        })
                    }
                    else -> {}
                }

            }
        }
        viewLifecycleOwner.launchStarted {
            viewModel.getSimilarMovies(args.movieModel.id).collect {
                when (it) {
                    is ResponseHandler.Success -> {
                        similarMoviesAdapter.submitList(it.data?.movieResults!!)
                    }
                    is ResponseHandler.Error -> {
                        d("ragacaeRori", it.errorMSg!!)
                    }
                }
            }
        }
    }

    private fun navigateToDetails(model: MovieModel) {
        findNavController().navigate(
            MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(
                model
            )
        )
    }
}
