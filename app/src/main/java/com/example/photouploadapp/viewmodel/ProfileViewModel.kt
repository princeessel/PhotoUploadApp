package com.example.photouploadapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photouploadapp.model.repository.PhotoUploadRepository
import com.example.photouploadapp.view.entities.User
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private lateinit var repository: PhotoUploadRepository

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val _logoutSuccess = MutableLiveData<Boolean>()
    val logoutSuccess: LiveData<Boolean> = _logoutSuccess

    private val _loggedInUser = MutableLiveData<User>()
    val loggedInUser: LiveData<User> = _loggedInUser


    fun init(repository: PhotoUploadRepository) {
        this.repository = repository
    }

    fun getUserData(id: Long) {
        viewModelScope.launch {
            val user = repository.getLoggedInUser(id)
            if (user == null) {
                _error.value = "User not found"
            } else {
                _loggedInUser.value = user
            }
        }

    }


    fun logout() {
//        viewModelScope.launch {
//            val user = repository.logout(id)
//            _logoutSuccess.value = true
//        }
    }

}

