package com.gabo.moviesapp.ui.loggedIn.home

import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gabo.moviesapp.databinding.FragmentHomeBinding
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.other.adapters.rvAdapters.PopularMoviesAdapter
import com.gabo.moviesapp.other.adapters.loadingAdapter.MoviesLoadingAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.NowStreamingMoviesAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.NowStreamingMoviesPagingAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import com.gabo.moviesapp.ui.loggedIn.ViewPagerContainerFragmentDirections
import kotlinx.android.synthetic.main.loading_item.*

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class,
    FragmentHomeBinding::inflate
) {
    private val popularMoviesAdapter: PopularMoviesAdapter by lazy {
        PopularMoviesAdapter {
            findNavController().navigate(
                ViewPagerContainerFragmentDirections.actionViewPagerContainerFragmentToMovieDetailsFragment(
                    it
                )
            )
        }.also {
            binding.rvPopularMovies.adapter = it
                .withLoadStateFooter(
                    footer = MoviesLoadingAdapter(requireContext()) { it.retry() })
            binding.rvPopularMovies.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
    private val nowStreamingMoviesAdapter: NowStreamingMoviesAdapter by lazy {
        NowStreamingMoviesAdapter {
            findNavController().navigate(
                ViewPagerContainerFragmentDirections.actionViewPagerContainerFragmentToMovieDetailsFragment(
                    it
                )
            )
        }.also {
            binding.rvNowStreamingMovies.adapter = it
            binding.rvNowStreamingMovies.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun setupView(savedInstanceState: Bundle?) {
        val genresList = (activity as MainActivity).genresList
        popularMoviesAdapter.submitList(genresList)
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        launchStarted(viewLifecycleOwner) {
            with(viewModel) {
                getNowPlayingMovies().collect {
                    when (it) {
                        is ResponseHandler.Success -> {
                            nowStreamingMoviesAdapter.submitList(it.data?.movieResults!!)
                        }
                        is ResponseHandler.Error -> {
                            Log.d("ragacaeRori", it.errorMSg!!)
                        }
                    }
                }
            }
        }
        launchStarted(viewLifecycleOwner) {
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
}

