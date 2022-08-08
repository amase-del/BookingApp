package com.example.bookingapp.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class LocalBooking(
    var timeStart: Long = System.currentTimeMillis(),
    var timeEnd: Long? = null,
    var connected: Boolean = false,
    var locallyDeleted: Boolean = false,

    @PrimaryKey(autoGenerate = false)
    val bookingId: String = UUID.randomUUID().toString()
): Serializable