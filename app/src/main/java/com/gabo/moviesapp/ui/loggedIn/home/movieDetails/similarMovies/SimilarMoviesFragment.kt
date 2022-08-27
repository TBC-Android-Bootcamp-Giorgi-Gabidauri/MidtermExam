package com.gabo.moviesapp.ui.loggedIn.home.movieDetails.similarMovies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.moviesapp.R
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.FragmentSimilarMoviesBinding
import com.gabo.moviesapp.other.adapters.rvAdapters.SimilarMoviesAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.other.common.setupAdapter
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.ui.MainViewModel
import com.gabo.moviesapp.ui.loggedIn.home.movieDetails.MovieDetailsFragmentArgs
import com.gabo.moviesapp.ui.loggedIn.home.movieDetails.MovieDetailsFragmentDirections

class SimilarMoviesFragment : BaseFragment<SimilarMoviesViewModel, FragmentSimilarMoviesBinding>(
    SimilarMoviesViewModel::class, FragmentSimilarMoviesBinding::inflate
) {
    private val activityViewModel: MainViewModel by activityViewModels()
    private lateinit var similarMoviesAdapter: SimilarMoviesAdapter

    override fun setupView(savedInstanceState: Bundle?) {
        setupAdapters()
        setupObservers()
        val genresList = (activity as MainActivity).genresList
        similarMoviesAdapter.submitGenresList(genresList)
    }

    private fun setupAdapters() {
        similarMoviesAdapter = binding.rvSimilarMovies.setupAdapter(SimilarMoviesAdapter {
            navigateToDetails(it)
        }, LinearLayoutManager(requireContext()))
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            activityViewModel.movieDetailsFragmentArgs?.collect {
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
}