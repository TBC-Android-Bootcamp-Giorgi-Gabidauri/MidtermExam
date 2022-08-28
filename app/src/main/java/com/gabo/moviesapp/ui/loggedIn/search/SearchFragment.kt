package com.gabo.moviesapp.ui.loggedIn.search

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.gabo.moviesapp.other.adapters.loadingAdapter.MoviesLoadingAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.PopularMoviesAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.isNetworkAvailable
import com.gabo.moviesapp.other.common.launchStarted
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
    private lateinit var searchAdapter: PopularMoviesAdapter
    private val activityViewModel: MainViewModel by activityViewModels()
    override fun setupView(savedInstanceState: Bundle?) {
        setupAdapters()
        setupSearchView()
    }

    private fun setupAdapters() {
        searchAdapter = PopularMoviesAdapter(itemClick = {
            navigateToDetails(it)
        }, { movieModel, i ->
            saveStateControl(movieModel)
            searchAdapter.notifyItemChanged(i)
        }).also {
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

        binding.searchView.doOnTextChanged { text, start, before, count ->
            @SuppressLint("NotifyDataSetChanged")
            if (isNetworkAvailable) {
//                    binding.tvNoInternet.gone()

                viewLifecycleOwner.launchStarted {
                    emptyRecyclerView(it)

                    viewModel.getSearchedMovies(text.toString()).collectLatest { pagingData ->
                        val data = pagingData.filter {
                            it.title.contains(text.toString())
                        }
                        searchAdapter.submitData(data)
                        searchAdapter.notifyDataSetChanged()
                    }
                    searchAdapter.loadStateFlow.collect { loadStates ->
                        pfRetryState.isVisible = loadStates.refresh !is LoadState.Loading
                    }
                }
            } else if (text != null && !isNetworkAvailable) {
                emptyRecyclerView(viewLifecycleOwner.lifecycleScope)

//                    binding.tvNoInternet.visible()
            }
        }

//        binding.searchView.setOnCloseListener {
//            binding.searchView.clearFocus()
//            emptyRecyclerView(viewLifecycleOwner.lifecycleScope)
//
//            if (!isNetworkAvailable) {
//                binding.tvNoInternet.visible()
//            } else {
//                binding.tvFindFavourite.visible()
//                binding.ivFragmentSearchIcon.visible()
//            }
//            false
//        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun emptyRecyclerView(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            searchAdapter.submitData(PagingData.empty())
            searchAdapter.notifyDataSetChanged()
        }
    }
    private fun saveStateControl(movieModel: MovieModel) {
        if (movieModel.isSaved == true) {
            movieModel.isSaved = false
            activityViewModel.deleteMovie(movieModel.id)
            ivSaveMovie.setImageResource(R.drawable.ic_save_item)
            Toast.makeText(requireContext(), "Removed", Toast.LENGTH_SHORT).show()
        } else {
            movieModel.isSaved = true
            activityViewModel.saveMovie(movieModel)
            ivSaveMovie.setImageResource(R.drawable.ic_save_item_filled)
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
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