package com.example.photouploadapp.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.photouploadapp.model.repository.PhotoUploadRepository
import com.example.photouploadapp.view.entities.Payment
import com.example.photouploadapp.view.entities.User
import kotlinx.coroutines.launch
import java.lang.Math.subtractExact
import java.text.NumberFormat.getCurrencyInstance
import java.util.*
import javax.inject.Inject
import kotlin.math.abs

class ProfileViewModel @Inject constructor(
    private val repository: PhotoUploadRepository
) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loggedInUser = MutableLiveData<User>()
    val loggedInUser: LiveData<User> = _loggedInUser

    private val _getTimeOfDay = MutableLiveData<Int>()
    val getTimeOfDay: LiveData<Int> = _getTimeOfDay

    private val _loanBalance = MutableLiveData<String>()
    val loanBalance: LiveData<String> = _loanBalance

    private val _paymentError = MutableLiveData<String>()
    val paymentError: LiveData<String> = _paymentError

    @RequiresApi(Build.VERSION_CODES.N)
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

    private fun currencyFormatter(currency: Long): String {
        return getCurrencyInstance(Locale(LANGUAGE, COUNTRY)).format(currency)
    }

    fun getTimeOfDay() {
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)

        _getTimeOfDay.value = timeOfDay
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getLoanBalance(id: Long) {
        viewModelScope.launch {
            val response = repository.getPaymentList(id)
            val user = repository.getLoggedInUser(id)

            if (response.isNotEmpty()) {
                val paymentId = getMaxPaymentId(response)

                val payment = repository.getPayment(paymentId)

                val balance = user.loanAmount?.let { abs(subtractExact(it, payment.paymentAmount)) }

                repository.getUpdatedUserLoanAmount(requireNotNull(balance))
                val updatedUser = repository.getLoggedInUser(id)

                _loanBalance.value = updatedUser.loanAmount.toString()
            } else {
                _loanBalance.value = user.loanAmount.toString()
            }
        }
    }

    private fun getMaxPaymentId(list: List<Payment>): Long {
        var paymentId = 0L
        if (list.size == 1) {
            paymentId = list[0].paymentId
        } else {
            list.forEach { payment ->
                paymentId = maxOf(payment.paymentId)
            }
        }
        return paymentId
    }

    companion object {
        private const val COUNTRY = "US"
        private const val LANGUAGE = "en"
    }
}
