package com.example.bookingapp.data.remote

import com.example.bookingapp.constants.Constants.API_VERSION
import com.example.bookingapp.data.remote.models.RemoteBooking
import com.example.bookingapp.data.remote.models.User
import com.example.bookingapp.data.remote.response.SimpleResponse
import retrofit2.http.*

interface BookingApi {

    @Headers("Content-type: application/json")
    @POST("$API_VERSION/users/register")
    suspend fun createAccount(
        @Body user: User
    ): SimpleResponse

    @Headers("Content-type: application/json")
    @POST("$API_VERSION/users/login")
    suspend fun login(
        @Body user: User
    ): SimpleResponse


    @Headers("Content-type: application/json")
    @POST("$API_VERSION/booking/create")
    suspend fun createBooking(
        @Header("Authorization") token: String,
        @Body booking: RemoteBooking
    ): SimpleResponse


    @Headers("Content-type: application/json")
    @GET("$API_VERSION/booking")
    suspend fun getAllBookingOneUser(
        @Header("Authorization") token: String
    ): List<RemoteBooking>

    @Headers("Content-type: application/json")
    @POST("$API_VERSION/booking/update")
    suspend fun updateBooking(
        @Header("Authorization") token: String,
        @Body booking: RemoteBooking
    ): SimpleResponse

    @Headers("Content-type: application/json")
    @DELETE("$API_VERSION/booking/delete")
    suspend fun deleteBooking(
        @Header("Authorization") token: String,
        @Query("id") bookingId: String
    ): SimpleResponse
}