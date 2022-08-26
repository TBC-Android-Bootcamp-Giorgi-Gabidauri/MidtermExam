package com.gabo.moviesapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.gabo.moviesapp.data.models.genreModels.GenreModel
import com.gabo.moviesapp.databinding.ActivityMainBinding
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    var genresList: List<GenreModel> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getGenres().collect {
                    when (it) {
                        is ResponseHandler.Success -> {
                            genresList = it.data!!.genres
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}