package com.gabo.moviesapp.ui.loggedIn.home.seeMoreNowStreaming

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.moviesapp.databinding.FragmentSeeMoreNowStreamingBinding
import com.gabo.moviesapp.other.adapters.loadingAdapter.MoviesLoadingAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.NowStreamingMoviesAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.NowStreamingMoviesPagingAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.PopularMoviesAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.ui.loggedIn.ViewPagerContainerFragmentDirections
import kotlinx.android.synthetic.main.loading_item.*

class SeeMoreNowStreamingFragment : BaseFragment<SeeMoreNowStreamingViewModel,FragmentSeeMoreNowStreamingBinding>(
    SeeMoreNowStreamingViewModel::class,FragmentSeeMoreNowStreamingBinding::inflate
) {
    private val seeMoreNowStreamingAdapter: PopularMoviesAdapter by lazy {
        PopularMoviesAdapter{
            findNavController().navigate(
                ViewPagerContainerFragmentDirections.actionViewPagerContainerFragmentToMovieDetailsFragment(
                    it
                )
            )
        }.also {
            binding.rvSeeMoreNowStreaming.adapter = it.withLoadStateFooter(
                    footer = MoviesLoadingAdapter(requireContext()) { it.retry() })
            binding.rvSeeMoreNowStreaming.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }
    override fun setupView(savedInstanceState: Bundle?) {
        setupObservers()
    }

    private fun setupObservers(){
        launchStarted(viewLifecycleOwner){
            viewModel.getNowPlayingMovies().collect{
                seeMoreNowStreamingAdapter.submitData(it)
            }
            seeMoreNowStreamingAdapter.loadStateFlow.collect { loadStates ->
                pfRetryState.isVisible = loadStates.refresh !is LoadState.Loading
            }
        }
    }
}