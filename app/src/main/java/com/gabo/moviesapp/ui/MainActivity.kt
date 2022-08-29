package com.gabo.moviesapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.gabo.moviesapp.data.models.genreModels.GenreModel
import com.gabo.moviesapp.databinding.ActivityMainBinding
import com.gabo.moviesapp.domain.ConnectionLiveData
import com.gabo.moviesapp.other.common.isNetworkAvailable
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    var genresList: List<GenreModel> = emptyList()
    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        connectionLiveData = ConnectionLiveData(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkNetwork()
        if (isNetworkAvailable) {
            viewModel.getUserInfo()
            setupObservers()
        }
    }

    private fun setupObservers() {
        launchStarted {
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

    private fun checkNetwork() {
        connectionLiveData.observe(this) { isConnected ->

            if (isConnected) {
                viewModel.getUserInfo()
                setupObservers()
            }

        }
    }
}
