package com.example.bookingapp.repository


import com.example.bookingapp.data.local.dao.BookingDao
import com.example.bookingapp.data.remote.BookingApi
import com.example.bookingapp.data.remote.models.User
import com.example.bookingapp.utils.ResultMessage
import com.example.bookingapp.utils.SessionManager
import com.example.bookingapp.utils.isNetworkConnected
import javax.inject.Inject

class BookingRepoImpl @Inject constructor(
    val bookingApi: BookingApi,
    val bookingDao: BookingDao,
    val sessionManager: SessionManager
): BookingRepo {

    override suspend fun createUser(user: User): ResultMessage<String> {

        return try {
            if(!isNetworkConnected(sessionManager.context)){
                ResultMessage.Error<String>("No Internet Connection!")
            }

            val result = bookingApi.createAccount(user)
            if(result.success){
                sessionManager.updateSession(result.message,user.name ?:"",user.email)
                ResultMessage.Success("User Created Successfully!")
            } else {
                ResultMessage.Error<String>(result.message)
            }
        }catch (e:Exception) {
            e.printStackTrace()
            ResultMessage.Error<String>(e.message ?: "Some Problem Occurred!")
        }

    }

    override suspend fun login(user: User): ResultMessage<String> {
        return try {
            if(!isNetworkConnected(sessionManager.context)){
                ResultMessage.Error<String>("No Internet Connection!")
            }

            val result = bookingApi.login(user)
            if(result.success){
                sessionManager.updateSession(result.message,user.name ?:"", user.email)
                ResultMessage.Success("Logged In Successfully!")
            } else {
                ResultMessage.Error<String>(result.message)
            }
        }catch (e:Exception) {
            e.printStackTrace()
            ResultMessage.Error<String>(e.message ?: "Some Problem Occurred!")
        }
    }

    override suspend fun getUser(): ResultMessage<User> {
        return try {
            val name = sessionManager.getCurrentUserName()
            val email = sessionManager.getCurrentUserEmail()
            if(name == null || email == null){
                ResultMessage.Error<User>("User not Logged In!")
            }
            ResultMessage.Success(User(name, email!!,"", ))
        } catch (e:Exception){
            e.printStackTrace()
            ResultMessage.Error(e.message ?: "Some Problem Occurred!")
        }
    }

    override suspend fun logout(): ResultMessage<String> {
        return try {
            sessionManager.logout()
            ResultMessage.Success("Logged Out Successfully!")
        } catch (e:Exception){
            e.printStackTrace()
            ResultMessage.Error(e.message ?: "Some Problem Occurred!")
        }
    }


}