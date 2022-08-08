package com.example.bookingapp.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookingapp.data.local.dao.BookingDao
import com.example.bookingapp.data.local.models.LocalBooking


@Database(
    entities = [LocalBooking::class],
    version = 1,
    exportSchema = false
)
abstract class BookingDatabase: RoomDatabase() {

    abstract fun getBookingDao(): BookingDao


}