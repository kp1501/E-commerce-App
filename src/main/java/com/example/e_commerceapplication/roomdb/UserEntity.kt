package com.example.e_commerceapplication.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserEntity (

    @PrimaryKey(autoGenerate = true)
    var userId : Int = 0,

    @ColumnInfo(name = "user_name")
    val userName : String,

    @ColumnInfo(name = "user_email")
    val userEmail : String,

    @ColumnInfo(name = "password")
    val password : String
)