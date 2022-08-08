package com.example.bookingapp.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookingapp.constants.Constants.MAXIMUM_PASSWORD_LENGTH
import com.example.bookingapp.constants.Constants.MINIMUM_PASSWORD_LENGTH
import com.example.bookingapp.data.remote.models.User
import com.example.bookingapp.repository.BookingRepo
import com.example.bookingapp.utils.ResultMessage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

class UserViewModel @Inject constructor(
    val bookingRepo: BookingRepo
): ViewModel() {
    private val _registerState = MutableSharedFlow<ResultMessage<String>>()
    val registerState:SharedFlow<ResultMessage<String>> = _registerState

    private val _loginState = MutableSharedFlow<ResultMessage<String>>()
    val loginState:SharedFlow<ResultMessage<String>> = _loginState

    private val _currentUserState = MutableSharedFlow<ResultMessage<User>>()
    val currentUserState:SharedFlow<ResultMessage<User>> = _currentUserState


    fun createUser(
        name: String,
        email: String,
        password: String
    ) = viewModelScope.launch {
        _registerState.emit(ResultMessage.Loading())

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() ) {
            _registerState.emit(ResultMessage.Error("Some Fields are empty"))
            return@launch
        }

        if (!isEmailValid(email)) {
            _registerState.emit(ResultMessage.Error("Email is not Valid!"))
            return@launch
        }

        if (!isPasswordValid(password)) {
            _registerState.emit(ResultMessage.Error("Password should be between $MINIMUM_PASSWORD_LENGTH and $MAXIMUM_PASSWORD_LENGTH"))
            return@launch
        }

        val newUser = User(
            name,
            email,
            password
        )
        _registerState.emit(bookingRepo.createUser(newUser))
    }

    fun loginUser(
        name: String,
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginState.emit(ResultMessage.Loading())

        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            _loginState.emit(ResultMessage.Error("Some Fields are empty"))
            return@launch
        }

        if (!isEmailValid(email)) {
            _loginState.emit(ResultMessage.Error("Email is not Valid!"))
            return@launch
        }

        if (!isPasswordValid(password)) {
            _loginState.emit(ResultMessage.Error("Password should be between $MINIMUM_PASSWORD_LENGTH and $MAXIMUM_PASSWORD_LENGTH"))
            return@launch
        }

        val newUser = User(
            name,
            email,
            password
        )

        _loginState.emit(bookingRepo.login(newUser))
    }


    fun getCurrentUser() = viewModelScope.launch {
        _currentUserState.emit(ResultMessage.Loading())
        _currentUserState.emit(bookingRepo.getUser())
    }

    fun logout() = viewModelScope.launch {
        val result = bookingRepo.logout()
        if (result is ResultMessage.Success) {
            getCurrentUser()
        }
    }


    private fun isEmailValid(email: String): Boolean {
        val regex =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        val pattern = Pattern.compile(regex)
        return (email.isNotEmpty() && pattern.matcher(email).matches())
    }

    private fun isPasswordValid(password: String): Boolean {

        return (password.length in MINIMUM_PASSWORD_LENGTH..MAXIMUM_PASSWORD_LENGTH)

    }
}