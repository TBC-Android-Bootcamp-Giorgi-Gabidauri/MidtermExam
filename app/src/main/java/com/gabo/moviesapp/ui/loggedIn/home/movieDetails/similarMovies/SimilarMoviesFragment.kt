package com.gabo.moviesapp.ui.loggedIn.home.movieDetails.similarMovies

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.moviesapp.R
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.FragmentSimilarMoviesBinding
import com.gabo.moviesapp.domain.ConnectionLiveData
import com.gabo.moviesapp.other.adapters.rvAdapters.SimilarMoviesAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.isNetworkAvailable
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.other.common.setupAdapter
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.ui.MainViewModel
import com.gabo.moviesapp.ui.loggedIn.home.movieDetails.MovieDetailsFragmentDirections
import kotlinx.android.synthetic.main.popular_movie_item.*

class SimilarMoviesFragment : BaseFragment<SimilarMoviesViewModel, FragmentSimilarMoviesBinding>(
    SimilarMoviesViewModel::class, FragmentSimilarMoviesBinding::inflate
) {
    private lateinit var connectionLiveData: ConnectionLiveData
    private val activityViewModel: MainViewModel by activityViewModels()
    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter

    override fun setupView(savedInstanceState: Bundle?) {
        connectionLiveData = ConnectionLiveData(requireContext())
        setupAdapters()
        checkNetwork()
        val genresList = (activity as MainActivity).genresList
        similarMoviesAdapter.submitGenresList(genresList)
        if (requireContext().isNetworkAvailable) {
            setupObservers()
        }
    }

    private fun setupAdapters() {
        similarMoviesAdapter =
            binding.rvSimilarMovies.setupAdapter(SimilarMoviesAdapter {
                navigateToDetails(it)
            }, LinearLayoutManager(requireContext()))
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            activityViewModel.movieDetailsFragmentArgs.collect {
                it.let { id ->
                    viewModel.getSimilarMovies(id).collect {
                        when (it) {
                            is ResponseHandler.Success -> {
                                similarMoviesAdapter.submitList(it.data?.movieResults!!)
                            }
                            is ResponseHandler.Error -> {
                                Log.d("ragacaeRori", it.errorMSg!!)
                            }
                        }
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

    private fun checkNetwork() {
        connectionLiveData.observe(this) { isConnected ->
            with(binding) {
                if (isConnected) {
                    binding.tvNoInternet.visibility = View.GONE
                    if (similarMoviesAdapter.itemCount > 0) {
                        progressBar.visibility = View.GONE
                    } else {
                        val genresList = (activity as MainActivity).genresList
                        similarMoviesAdapter.submitGenresList(genresList)
                        setupObservers()
                    }
                } else {
                    binding.tvNoInternet.visibility = View.VISIBLE
                }
            }
        }
    }
}