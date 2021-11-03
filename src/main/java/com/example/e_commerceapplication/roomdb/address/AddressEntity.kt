package com.example.e_commerceapplication.roomdb.address

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class AddressEntity(

    @PrimaryKey(autoGenerate = true)
    val addressId: Int = 0,

    @ColumnInfo(name = "address")
    val address: String
)
