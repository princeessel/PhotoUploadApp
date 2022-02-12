package com.example.photouploadapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photouploadapp.model.repository.PhotoUploadRepository
import com.example.photouploadapp.view.entities.User
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private lateinit var repository: PhotoUploadRepository

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loginUser = MutableLiveData<User>()
    val loginUser: LiveData<User> = _loginUser


    fun init(repository: PhotoUploadRepository) {
        this.repository = repository
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val user = repository.getUser(username, password)
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
}
