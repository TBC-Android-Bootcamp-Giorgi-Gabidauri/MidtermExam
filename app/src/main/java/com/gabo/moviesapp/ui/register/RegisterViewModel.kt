package com.gabo.moviesapp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabo.moviesapp.data.models.userModels.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val db = FirebaseDatabase.getInstance().getReference("UserData")

    private var _registrationState = MutableStateFlow("")
    val registrationState = _registrationState.asStateFlow()

    fun registerUser(username: String, email: String, password: String) {
        viewModelScope.launch {
            Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val userInfo = UserModel(username, email, password)
                    db.child(FirebaseAuth.getInstance().currentUser?.uid!!).setValue(userInfo)
                    _registrationState.value = "Successful"
                } else {
                    _registrationState.value = it.exception?.message.toString()
                }
            }
        }
    }

}