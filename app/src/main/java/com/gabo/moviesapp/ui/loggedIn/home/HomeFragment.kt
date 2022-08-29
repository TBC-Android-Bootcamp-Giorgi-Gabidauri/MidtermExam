package com.gabo.moviesapp.ui.loggedIn.home

import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gabo.moviesapp.R
import com.gabo.moviesapp.data.models.genreModels.GenreModel
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.FragmentHomeBinding
import com.gabo.moviesapp.domain.ConnectionLiveData
import com.gabo.moviesapp.other.adapters.loadingAdapter.MoviesLoadingAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.PopularMoviesAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.BASE_IMAGE_URL
import com.gabo.moviesapp.other.common.isNetworkAvailable
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.other.common.setNowStreamingData
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.ui.MainViewModel
import com.gabo.moviesapp.ui.loggedIn.ViewPagerContainerFragmentDirections
import kotlinx.android.synthetic.main.loading_item.*
import kotlinx.android.synthetic.main.popular_movie_item.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class,
    FragmentHomeBinding::inflate
) {
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private val activityViewModel: MainViewModel by activityViewModels()
    private var nowStreamingMoviesList = emptyList<MovieModel>()
    private lateinit var genresList: List<GenreModel>

    override fun setupView(savedInstanceState: Bundle?) {
        connectionLiveData = ConnectionLiveData(requireContext())
        genresList = (activity as MainActivity).genresList
        setupAdapters()
        setupAdapters()
        checkNetwork()
        setupClickListeners()
        if (!requireContext().isNetworkAvailable) {
            with(binding) {
                tvNoInternet.visibility = View.VISIBLE
                nsvHome.visibility = View.GONE
            }
        } else {
            setupObservers()
        }
    }

    private fun setNowStreamingData(list: List<MovieModel>) {
        if (nowStreamingMoviesList.isNotEmpty()) {
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
            with(binding) {
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
        if (requireContext().isNetworkAvailable) {
            viewLifecycleOwner.launchStarted {
                with(viewModel) {
                    getNowPlayingMovies().collect {
                        when (it) {
                            is ResponseHandler.Success -> {
                                nowStreamingMoviesList = it.data?.movieResults?.take(11)!!
                                setNowStreamingData(nowStreamingMoviesList)
                                binding.llNowStreamingMovies.isVisible = true
                                binding.swipeRefresh.isRefreshing = false
                            }
                            is ResponseHandler.Error -> {
                                d("ragacaeRori", it.errorMSg!!)
                                binding.swipeRefresh.isRefreshing = false
                            }
                        }
                    }
                }
            }

            viewLifecycleOwner.launchStarted { scope ->
                with(viewModel) {
                    getPopularMovies().collect { pagingData ->
                        activityViewModel.getGenres()
                        delay(500)
                        val genresList = (activity as MainActivity).genresList
                        popularMoviesAdapter.submitList(genresList)
                        popularMoviesAdapter.submitData(pagingData)
                    }
                }
                popularMoviesAdapter.loadStateFlow.collect { loadStates ->
                    pfRetryState.isVisible = loadStates.refresh !is LoadState.Loading
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnSeeMoreNowStreaming.setOnClickListener {
            findNavController().navigate(ViewPagerContainerFragmentDirections.actionViewPagerContainerFragmentToSeeMoreNowStreamingFragment())
        }
        binding.swipeRefresh.setOnRefreshListener {
            binding.llHome.visibility = View.GONE
            setupObservers()
            binding.llHome.visibility = View.VISIBLE
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun navigateToDetails(model: MovieModel) {
        if(requireContext().isNetworkAvailable){
            findNavController().navigate(
                ViewPagerContainerFragmentDirections.actionViewPagerContainerFragmentToMovieDetailsFragment(
                    model
                )
            )
        }
    }

    private fun checkNetwork() {
        connectionLiveData.observe(this) { isConnected ->
            with(binding) {
                if (isConnected) {
                    tvNoInternet.visibility = View.GONE
                    nsvHome.visibility = View.VISIBLE
                    if (popularMoviesAdapter.itemCount > 0) {
                        progressBar.visibility = View.GONE
                    } else {
                        setupObservers()
                    }
                } else {
                    tvNoInternet.visibility = View.VISIBLE
                }
            }
        }
    }
}

