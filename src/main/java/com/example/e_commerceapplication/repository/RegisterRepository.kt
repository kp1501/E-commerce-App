package com.example.e_commerceapplication.repository

import com.example.e_commerceapplication.roomdb.Database
import com.example.e_commerceapplication.roomdb.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterRepository @Inject constructor (appDb: Database) {

    private val userDao = appDb.registerDatabaseDao()

    fun getId(user: UserEntity): Int{
        return userDao.getId(user.userEmail)
    }

    suspend fun insert(user : UserEntity){
        userDao.insert(user)
    }

    fun getUser(user: UserEntity): UserEntity?{
        return userDao.getUser(user.userEmail, user.password)
    }
}
