package com.example.bookingapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookingapp.data.local.models.LocalBooking
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: LocalBooking)

    @Query("SELECT * FROM LocalBooking WHERE locallyDeleted = 0 ORDER BY timeStart DESC")
    fun getAllNotesOrderedByDate(): Flow<List<LocalBooking>>

    @Query("DELETE FROM LocalBooking WHERE bookingId=:bookingId")
    suspend fun deleteBooking(bookingId: String)

    @Query("UPDATE LocalBooking SET locallyDeleted = 1 WHERE bookingId =:bookingId")
    suspend fun deleteBookingLocally(bookingId: String)

    @Query("SELECT * FROM LocalBooking WHERE connected = 0")
    suspend fun getAllLocalBooking(): List<LocalBooking>

    @Query("SELECT * FROM LocalBooking WHERE locallyDeleted = 1")
    suspend fun getAllLocallyBooking(): List<LocalBooking>


}