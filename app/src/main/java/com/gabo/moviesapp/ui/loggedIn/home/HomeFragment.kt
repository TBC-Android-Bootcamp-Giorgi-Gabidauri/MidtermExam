package com.gabo.moviesapp.ui.loggedIn.home

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.moviesapp.databinding.FragmentHomeBinding
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.util.adapters.rvAdapters.PopularMoviesAdapter
import com.gabo.moviesapp.util.adapters.loadingAdapter.MoviesLoadingAdapter
import com.gabo.moviesapp.util.adapters.rvAdapters.NowPlayingMoviesAdapter
import com.gabo.moviesapp.util.base.BaseFragment
import com.gabo.moviesapp.util.common.launchStarted
import kotlinx.android.synthetic.main.loading_item.*

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class,
    FragmentHomeBinding::inflate
) {
    private val popularMoviesAdapter: PopularMoviesAdapter by lazy {
        PopularMoviesAdapter {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(it))
        }.also {
            binding.rvPopularMovies.adapter = it
//                .withLoadStateFooter(
//                footer = MoviesLoadingAdapter(requireContext()) { it.retry() })
            binding.rvPopularMovies.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
    private val nowPlayingMoviesAdapter: NowPlayingMoviesAdapter by lazy {
        NowPlayingMoviesAdapter {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieDetailsFragment(it))
        }.also {
            binding.rvNowPlayingMovies.adapter = it.withLoadStateFooter(
                footer = MoviesLoadingAdapter(requireContext()) { it.retry() })
            binding.rvNowPlayingMovies.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun setupView(savedInstanceState: Bundle?) {
//        launchStarted(viewLifecycleOwner){
//            popularMoviesAdapter.submitData(PagingData.empty())
//        }
        val genresList = (activity as MainActivity).genresList
        popularMoviesAdapter.submitList(genresList)
        setupObservers()
    }

    private fun setupObservers() {
        launchStarted(viewLifecycleOwner){
            with(viewModel) {
                getNowPlayingMovies().collect {
                    nowPlayingMoviesAdapter.submitData(it)
                }
            }
            nowPlayingMoviesAdapter.loadStateFlow.collect { loadStates ->
                pfRetryState.isVisible = loadStates.refresh !is LoadState.Loading
            }
        }
        launchStarted(viewLifecycleOwner){
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
}

