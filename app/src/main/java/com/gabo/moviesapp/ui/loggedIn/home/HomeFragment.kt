package com.gabo.moviesapp.ui.loggedIn.home

import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gabo.moviesapp.R
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


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>(
    HomeViewModel::class,
    FragmentHomeBinding::inflate
) {
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private val activityViewModel: MainViewModel by activityViewModels()
    private var nowStreamingMoviesList = emptyList<MovieModel>()

    override fun setupView(savedInstanceState: Bundle?) {
        connectionLiveData = ConnectionLiveData(requireContext())
        if (!requireContext().isNetworkAvailable) {
            with(binding) {
                tvNoInternet.visibility = View.VISIBLE
                rvPopularMovies.visibility = View.INVISIBLE
                llNowStreamingMovies.visibility = View.GONE
                btnSeeMoreNowStreaming.visibility = View.GONE
                tvPopular.visibility = View.GONE
                tvNowStreaming.visibility = View.GONE
            }
        }
        checkNetwork()
        setupAdapters()
        setupClickListeners()
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
        popularMoviesAdapter = PopularMoviesAdapter(itemClick = {
            navigateToDetails(it)
        }, { movieModel, i ->
            saveStateControl(movieModel)
            popularMoviesAdapter.notifyItemChanged(i)
        }).also {
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

    private fun setupObservers() {
        if (requireContext().isNetworkAvailable) {
            binding.progressBar.isVisible = true
            viewLifecycleOwner.launchStarted {
                with(viewModel) {
                    getNowPlayingMovies().collect {
                        when (it) {
                            is ResponseHandler.Success -> {
                                nowStreamingMoviesList = it.data?.movieResults?.take(11)!!
                                setNowStreamingData(nowStreamingMoviesList)
                                binding.llNowStreamingMovies.isVisible = true
                                binding.progressBar.isVisible = false
                            }
                            is ResponseHandler.Error -> {
                                d("ragacaeRori", it.errorMSg!!)
                                binding.progressBar.isVisible = false
                            }
                        }
                    }
                }
            }

            viewLifecycleOwner.launchStarted {scope->
                with(viewModel) {
                    getPopularMovies().collect {
                        it.map {movieModel ->
                            checkIfMovieExist(movieModel.id)
                            movieExist.collect{savedOrNot->
                                movieModel.isSaved = savedOrNot
                            }
                        }
                        popularMoviesAdapter.submitData (it)
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
    }

    private fun navigateToDetails(model: MovieModel) {
        findNavController().navigate(
            ViewPagerContainerFragmentDirections.actionViewPagerContainerFragmentToMovieDetailsFragment(
                model
            )
        )
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
                        val genresList = (activity as MainActivity).genresList
                        popularMoviesAdapter.submitList(genresList)
                        setupObservers()
                    }
                } else {
                    tvNoInternet.visibility = View.VISIBLE
                }
            }
        }
    }
}

