package com.gabo.moviesapp.ui.loggedIn.profile

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.moviesapp.R
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.FragmentProfileBinding
import com.gabo.moviesapp.other.adapters.rvAdapters.SimilarMoviesAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.other.common.setupAdapter
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.ui.MainViewModel
import com.gabo.moviesapp.ui.loggedIn.ViewPagerContainerFragmentDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.popular_movie_item.*
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<ProfileViewModel,FragmentProfileBinding>(
    ProfileViewModel::class,FragmentProfileBinding::inflate
) {

    private lateinit var savedItemsAdapter: SimilarMoviesAdapter
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun setupView(savedInstanceState: Bundle?) {
        setUserInfo()
        listeners()
        setupAdapters()
    }

    private fun listeners(){
        binding.ivLogOut.setOnClickListener {
            Firebase.auth.signOut()
            findNavController().navigate(ViewPagerContainerFragmentDirections.actionViewPagerContainerFragmentToLogInFragment())
        }
    }

    private fun setUserInfo() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                activityViewModel.userInfo.collect{
                    if (it != null){
                        binding.tvUsername.text = it.userName
                        binding.tvUserEmail.text = it.email
                    }
                }
            }
        }
    }

    private fun setupAdapters() {
        savedItemsAdapter = binding.rvSavedMovies.setupAdapter(
            SimilarMoviesAdapter({}, {
                saveStateControl(it)
                setupObservers()
            }),
            LinearLayoutManager(requireContext())
        )
        val genresList = (activity as MainActivity).genresList
        savedItemsAdapter.submitGenresList(genresList)
    }

    private fun setupObservers() {
        viewModel.getMovies()
        viewLifecycleOwner.launchStarted {
            viewModel.movies.collect {
                savedItemsAdapter.submitList(it)
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

    override fun onResume() {
        super.onResume()
        setupObservers()
    }

}