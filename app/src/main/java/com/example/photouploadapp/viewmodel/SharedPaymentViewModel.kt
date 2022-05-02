package com.example.photouploadapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photouploadapp.model.repository.PhotoUploadRepository
import com.example.photouploadapp.view.entities.Payment
import kotlinx.coroutines.launch

import javax.inject.Inject

class SharedPaymentViewModel @Inject constructor(
    val repository: PhotoUploadRepository
) : ViewModel() {

    fun createPayment(userId: Long, payment: Long, date: String, account: String) {
        viewModelScope.launch {
            val user = repository.getLoggedInUser(userId)
            val newPayment = Payment(
                user.userId,
                payment,
                date,
                account
            )

            repository.createPayment(newPayment)
        }
    }

}
