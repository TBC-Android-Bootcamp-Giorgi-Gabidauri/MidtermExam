package com.gabo.moviesapp.ui.logIn

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInViewModel : ViewModel() {

    fun logIn(email: String, password: String) =
        Firebase.auth.signInWithEmailAndPassword(email, password)
}