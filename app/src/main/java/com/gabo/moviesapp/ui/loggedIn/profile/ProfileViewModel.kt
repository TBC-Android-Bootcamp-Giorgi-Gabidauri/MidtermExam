package com.gabo.moviesapp.ui.loggedIn.profile

import androidx.lifecycle.ViewModel
import com.gabo.moviesapp.data.models.userModels.UserModel
import com.gabo.moviesapp.ui.register.db
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {

    private val _userInfo = MutableStateFlow<UserModel?>(null)
    val userInfo = _userInfo.asStateFlow()

    fun getUserInfo(){
        db.child(FirebaseAuth.getInstance().currentUser?.uid!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue(UserModel::class.java)?: return
                _userInfo.value = userInfo
            }


            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}