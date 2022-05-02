package com.example.photouploadapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photouploadapp.model.repository.PhotoUploadRepository
import com.example.photouploadapp.view.entities.User
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: PhotoUploadRepository
) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loginUser = MutableLiveData<User>()
    val loginUser: LiveData<User> = _loginUser


    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.getUser(username, password)
            loginUser(user, password)
        }

    }


    private fun loginUser(user: User, password: String) {
        if (user == null) {
            _error.value = "User not found"
        } else {
            if (user.password == password) {
                _loginUser.value = user
            } else {
                _error.value = "Password is not valid"
            }
        }

    }
}
