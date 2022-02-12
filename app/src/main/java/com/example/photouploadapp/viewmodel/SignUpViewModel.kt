package com.example.photouploadapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photouploadapp.model.repository.PhotoUploadRepository
import com.example.photouploadapp.view.entities.User
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private lateinit var repository: PhotoUploadRepository

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _signUpSuccess = MutableLiveData<Boolean>()
    val signUpSuccess: LiveData<Boolean> = _signUpSuccess


    fun init(repository: PhotoUploadRepository) {
        this.repository = repository
    }

    fun signUp(username: String, email: String, password: String, fullName: String) {
        viewModelScope.launch {
            val user = repository.getUser(username, password)

            if (user != null) {
                _error.value = "User Already exist"
            } else {
                val newUser = User(username, email, password, fullName)
                repository.createUser(newUser)

                _signUpSuccess.value = true
            }
        }

    }
}
