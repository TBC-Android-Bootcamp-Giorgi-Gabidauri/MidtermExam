package com.gabo.moviesapp.ui.register

import android.provider.ContactsContract
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabo.moviesapp.data.models.userModels.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private var _registrationState = MutableSharedFlow<String>()
    val registrationState = _registrationState.asSharedFlow()

    fun registerUser(username: String, email: String, password: String){
        var state = ""
        viewModelScope.launch {
            Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                state = if (it.isSuccessful) {
                    val userInfo = UserModel(username, email, password)
                    db.child(FirebaseAuth.getInstance().currentUser?.uid!!).setValue(userInfo)
                    "Successful"
                }else{
                    "Error!"
                }
            }
            _registrationState.emit(state)
        }
    }

}