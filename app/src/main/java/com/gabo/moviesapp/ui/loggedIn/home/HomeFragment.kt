package com.gabo.moviesapp.ui.loggedIn.home

import android.os.Bundle
import android.util.Log.d
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.FragmentHomeBinding
import com.gabo.moviesapp.other.adapters.loadingAdapter.MoviesLoadingAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.PopularMoviesAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.BASE_IMAGE_URL
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.other.common.setNowStreamingData
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.ui.loggedIn.ViewPagerContainerFragmentDirections
import kotlinx.android.synthetic.main.loading_item.*


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class,
    FragmentHomeBinding::inflate
) {
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private var nowStreamingMoviesList = emptyList<MovieModel>()

    override fun setupView(savedInstanceState: Bundle?) {
        setupAdapters()
        val genresList = (activity as MainActivity).genresList
        popularMoviesAdapter.submitList(genresList)
        setupObservers()
        setupClickListeners()
    }

    private fun setNowStreamingData(list: List<MovieModel>) {
        if (nowStreamingMoviesList.isNotEmpty()){
            with(binding) {
                nsMovieFirst.setNowStreamingData(list[8]) { navigateToDetails(it) }
                nsMovieSecond.setNowStreamingData(list[10]) { navigateToDetails(it) }
                nsMovieThird.setNowStreamingData(list[2]) { navigateToDetails(it) }
            }
        }
    }

    private fun setupAdapters() {
        popularMoviesAdapter = PopularMoviesAdapter {
            navigateToDetails(it)
        }.also {
            with(binding){
                rvPopularMovies.adapter = it
                    .withLoadStateFooter(
                        footer = MoviesLoadingAdapter(requireContext()) { it.retry() })
                rvPopularMovies.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                rvPopularMovies.setItemViewCacheSize(20)
            }
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            with(viewModel) {
                getNowPlayingMovies().collect {
                    when (it) {
                        is ResponseHandler.Success -> {
                            nowStreamingMoviesList = it.data?.movieResults?.take(11)!!
                            setNowStreamingData(nowStreamingMoviesList)
                        }
                        is ResponseHandler.Error -> {
                            d("ragacaeRori", it.errorMSg!!)
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.launchStarted {
            with(viewModel) {
                getPopularMovies().collect {
                    popularMoviesAdapter.submitData(it)
                }
            }
            popularMoviesAdapter.loadStateFlow.collect { loadStates ->
                pfRetryState.isVisible = loadStates.refresh !is LoadState.Loading
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnSeeMoreNowStreaming.setOnClickListener {
            findNavController().navigate(ViewPagerContainerFragmentDirections.actionViewPagerContainerFragmentToSeeMoreNowStreamingFragment())
        }
    }

    private fun navigateToDetails(model: MovieModel) {
        findNavController().navigate(
            ViewPagerContainerFragmentDirections.actionViewPagerContainerFragmentToMovieDetailsFragment(
                model
            )
        )
    }
}

