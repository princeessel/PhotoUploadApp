package com.example.photouploadapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photouploadapp.model.repository.PhotoUploadRepository
import com.example.photouploadapp.view.entities.Payment
import com.example.photouploadapp.view.entities.User
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserActivitiesViewModel @Inject constructor(
    private val repository: PhotoUploadRepository
) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _payments = MutableLiveData<List<Payment>>()
    val payments: LiveData<List<Payment>> = _payments


    fun getPaymentList(userId: Long) {
        viewModelScope.launch {
            val payments = repository.getPaymentList(userId)

            _payments.value = payments
        }
    }
}
