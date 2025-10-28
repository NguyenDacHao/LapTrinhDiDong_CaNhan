package com.example.uth.model

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class UserViewModel : ViewModel() {
    var user: FirebaseUser? = null
    var userDob: String? = null
        private set
}
