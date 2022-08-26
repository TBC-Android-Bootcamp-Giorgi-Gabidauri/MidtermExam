package com.gabo.moviesapp.ui.loggedIn.home.movieDetails

import android.os.Bundle
import android.util.Log.d
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gabo.moviesapp.data.models.genreModels.GenreModel
import com.gabo.moviesapp.data.models.movieTrailerModels.MovieTrailersModel
import com.gabo.moviesapp.other.adapters.rvAdapters.SimilarMoviesAdapter
import com.gabo.moviesapp.databinding.FragmentMovieDetailsBinding
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.other.adapters.genresAdapter.GenresAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.BASE_IMAGE_URL
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MovieDetailsFragment : BaseFragment<MovieDetailsViewModel, FragmentMovieDetailsBinding>(
    MovieDetailsViewModel::class,
    FragmentMovieDetailsBinding::inflate
) {
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val genresAdapter: GenresAdapter by lazy {
        GenresAdapter().also {
            binding.rvGenres.adapter = it
            binding.rvGenres.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
        }
    }
    private val similarMoviesAdapter: SimilarMoviesAdapter by lazy {
        SimilarMoviesAdapter() {
            findNavController().navigate(
                MovieDetailsFragmentDirections.actionMovieDetailsFragmentSelf(
                    it
                )
            )
        }.also {
            binding.rvSimilarMovies.adapter = it
//                .withLoadStateFooter(
//                footer = MoviesLoadingAdapter(requireContext()) { it.retry() })
            binding.rvSimilarMovies.layoutManager =
                LinearLayoutManager(requireContext())
        }
    }

    override fun setupView(savedInstanceState: Bundle?) {
//        viewModel.getMovieVideos(args.movieModel.id)
//        viewModel.getSimilarMovies(args.movieModel.id)
        setupObservers()
        setDetails()
        val genresList = (activity as MainActivity).genresList
        similarMoviesAdapter.submitGenresList(genresList)
    }

    private fun setDetails() {
        val movieModel = args.movieModel
        with(binding) {
            Glide.with(requireContext()).load(BASE_IMAGE_URL + movieModel.backdropPath)
                .into(ivPoster)
            tvDescriptionText.text = movieModel.overview
            tvTitle.text = movieModel.title
            tvRating.text = movieModel.Rating.toString()

            val genresToFilter = (activity as MainActivity).genresList.toMutableList()
            val filtered = mutableListOf<GenreModel>()
            movieModel.genreIds.forEach { genreId ->
                genresToFilter.forEach { genreModel ->
                    if (genreId == genreModel.id) {
                        filtered.add(genreModel)
                    }
                }
            }
            genresAdapter.submitList(filtered.toList())
        }
    }

    private fun setupObservers() {
        launchStarted(viewLifecycleOwner) {
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
        launchStarted(viewLifecycleOwner) {
            viewModel.getSimilarMovies(args.movieModel.id).collect {
                when(it){
                    is ResponseHandler.Success -> {
                        similarMoviesAdapter.submitList(it.data?.movieResults)
                    }
                    is ResponseHandler.Error ->{
                        d("ragacaeRori",it.errorMSg!!)
                    }
                }
            }
//            similarMoviesAdapter.loadStateFlow.collect { loadStates ->
//                pfRetryState.isVisible = loadStates.refresh !is LoadState.Loading
//            }
        }
    }
}