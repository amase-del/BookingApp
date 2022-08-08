package com.example.bookingapp.di

import android.content.Context
import android.provider.DocumentsContract
import androidx.room.Room
import com.example.bookingapp.constants.Constants.BASE_URL
import com.example.bookingapp.data.local.BookingDatabase
import com.example.bookingapp.data.local.dao.BookingDao
import com.example.bookingapp.data.remote.BookingApi
import com.example.bookingapp.repository.BookingRepo
import com.example.bookingapp.repository.BookingRepoImpl
import com.example.bookingapp.utils.SessionManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Singleton
    @Provides
    fun provideSessionManager(
        @ApplicationContext context: Context
    ) = SessionManager(context)

    @Singleton
    @Provides
    fun provideBookingDatabase(
        @ApplicationContext context: Context
    ): BookingDatabase = Room.databaseBuilder(
        context,
        BookingDatabase::class.java,
        "booking_db"
    ).build()

    @Singleton
    @Provides
    fun provideBookingDao(
        bookingDb: BookingDatabase
    ) = bookingDb.getBookingDao()

    @Singleton
    @Provides
    fun provideBookingApi(): BookingApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookingApi::class.java)


    }

    @Singleton
    @Provides
    fun provideBookingRepo(
        bookingApi: BookingApi,
        bookingDao: BookingDao,
        sessionManager: SessionManager
    ): BookingRepo {
        return BookingRepoImpl(
            bookingApi,
            bookingDao,
            sessionManager
        )
    }
}