package com.example.e_commerceapplication.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM User where user_email= :userEmail and password= :password")
    fun getUser(userEmail: String?, password: String?): UserEntity?

    @Query("SELECT userId FROM User where user_email= :userEmail")
    fun getId(userEmail: String?): Int
}