package com.gabo.moviesapp.ui.loggedIn.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.moviesapp.R
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.FragmentSearchBinding
import com.gabo.moviesapp.domain.ConnectionLiveData
import com.gabo.moviesapp.other.adapters.loadingAdapter.MoviesLoadingAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.PopularMoviesAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.isNetworkAvailable
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.ui.MainViewModel
import com.gabo.moviesapp.ui.loggedIn.ViewPagerContainerFragmentDirections
import kotlinx.android.synthetic.main.loading_item.*
import kotlinx.android.synthetic.main.popular_movie_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>(
    SearchViewModel::class, FragmentSearchBinding::inflate
) {
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var searchAdapter: PopularMoviesAdapter
    override fun setupView(savedInstanceState: Bundle?) {
        connectionLiveData = ConnectionLiveData(requireContext())
        setupAdapters()
        setupSearchView()
    }

    private fun setupAdapters() {
        searchAdapter = PopularMoviesAdapter {
            navigateToDetails(it)
        }.also {
            with(binding) {
                rvSearch.adapter = it
                    .withLoadStateFooter(
                        footer = MoviesLoadingAdapter(requireContext()) { it.retry() })
                rvSearch.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                rvSearch.setItemViewCacheSize(20)
            }
        }
    }

    private fun setupSearchView() {
        val isNetworkAvailable = requireContext().isNetworkAvailable

        binding.searchView.doOnTextChanged { text, _, _, _ ->
            @SuppressLint("NotifyDataSetChanged")
            if (requireContext().isNetworkAvailable) {
                binding.tvNoInternet.visibility = View.GONE
                viewLifecycleOwner.launchStarted {
                    emptyRecyclerView(it)

                    viewModel.getSearchedMovies(text.toString()).collectLatest { pagingData ->
                        val data = pagingData.filter { movieModel ->
                            movieModel.title.contains(text.toString())
                        }
                        val genresList = (activity as MainActivity).genresList
                        searchAdapter.submitList(genresList)
                        searchAdapter.submitData(data)
                        searchAdapter.notifyDataSetChanged()
                    }
                    searchAdapter.loadStateFlow.collect { loadStates ->
                        pfRetryState.isVisible = loadStates.refresh !is LoadState.Loading
                    }
                }
            } else if (text != null && !isNetworkAvailable) {
                emptyRecyclerView(viewLifecycleOwner.lifecycleScope)
                binding.tvNoInternet.visibility = View.VISIBLE
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun emptyRecyclerView(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            searchAdapter.submitData(PagingData.empty())
            searchAdapter.notifyDataSetChanged()
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