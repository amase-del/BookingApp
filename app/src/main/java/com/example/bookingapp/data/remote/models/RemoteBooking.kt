package com.example.bookingapp.data.remote.models

data class RemoteBooking(
    val timeStart: Long,
    val timeEnd: Long,
    val bookingId: String
) {
}