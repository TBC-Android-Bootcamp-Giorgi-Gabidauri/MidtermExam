package com.gabo.moviesapp.ui.loggedIn.home.movieDetails

import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.gabo.moviesapp.R
import com.gabo.moviesapp.data.models.genreModels.GenreModel
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.data.models.movieTrailerModels.MovieTrailersModel
import com.gabo.moviesapp.databinding.FragmentMovieDetailsBinding
import com.gabo.moviesapp.other.adapters.genresAdapter.GenresAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.SimilarMoviesAdapter
import com.gabo.moviesapp.other.adapters.viewPagerAdapter.TabLayoutAdapter
import com.gabo.moviesapp.other.adapters.viewPagerAdapter.ViewPagerAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.*
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.ui.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.fragment_view_pager_container.*
import kotlinx.android.synthetic.main.popular_movie_item.*
import kotlinx.coroutines.flow.collect

class MovieDetailsFragment : BaseFragment<MovieDetailsViewModel, FragmentMovieDetailsBinding>(
    MovieDetailsViewModel::class,
    FragmentMovieDetailsBinding::inflate
) {
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val activityViewModel: MainViewModel by activityViewModels()

    private lateinit var movieModel: MovieModel
    override fun setupView(savedInstanceState: Bundle?) {
        setDetails()
        setupTabLayout()
        setupClickListeners()
    }

    private fun setupClickListeners() {
        with(binding) {
            ivSaveMovie.setOnClickListener {
                saveStateControl()
            }

            ivBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun saveStateControl() {
        with(binding) {
            viewModel.checkIfMovieExist(movieModel.id)
            viewLifecycleOwner.launchStarted {
                viewModel.movieExist.collect{
                    if (it) {
                        activityViewModel.deleteMovie(movieModel.id)
                        ivSaveMovie.setImageResource(R.drawable.ic_save_item)
                        Toast.makeText(requireContext(), "Removed", Toast.LENGTH_SHORT).show()
                    } else {
                        activityViewModel.saveMovie(movieModel)
                        ivSaveMovie.setImageResource(R.drawable.ic_save_item_filled)
                        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setDetails() {
        activityViewModel.movieDetailsFragmentArgs.value = args.movieModel?.id ?: 0
        movieModel = args.movieModel!!
        val genresToFilter = (activity as MainActivity).genresList
        with(binding) {
            ivPoster.loadImage(movieModel.backdropPath)
            tvDescriptionText.text = movieModel.overview
            tvTitle.text = movieModel.title
            tvRating.text = movieModel.Rating.toString()
            rvGenres.setupGenres(genresToFilter, movieModel)
            viewModel.checkIfMovieExist(movieModel.id)
            setupObservers()
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            with(binding) {
                viewModel.movieExist.collect {
                    if (it) {
                        ivSaveMovie.setImageResource(R.drawable.ic_save_item_filled)
                    } else {
                        ivSaveMovie.setImageResource(R.drawable.ic_save_item)
                    }
                }
            }
        }
    }

    private fun setupTabLayout() {
        with(binding) {
            binding.viewPagerForTL.adapter = TabLayoutAdapter(requireActivity())
            binding.viewPagerForTL.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    with(tabLayout) {
                        when (position) {
                            0 -> {
                                getTabAt(0)
                                selectTab(getTabAt(0))
                            }
                            else -> {
                                getTabAt(1)
                                selectTab(getTabAt(1))
                            }
                        }
                    }
                }
            })
        }
        binding.tabLayout.apply {
            addTab(this.newTab().setText("Related Videos"))
            addTab(this.newTab().setText("Similar Movies"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.position?.let { binding.viewPagerForTL.currentItem = it }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }
}