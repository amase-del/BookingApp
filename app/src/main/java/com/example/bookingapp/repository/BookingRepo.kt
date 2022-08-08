package com.example.bookingapp.repository

import com.example.bookingapp.data.remote.models.User
import com.example.bookingapp.utils.ResultMessage

interface BookingRepo {

    suspend fun createUser(user: User): ResultMessage<String>
    suspend fun login(user: User): ResultMessage<String>
    suspend fun getUser(): ResultMessage<User>
    suspend fun logout(): ResultMessage<String>
}