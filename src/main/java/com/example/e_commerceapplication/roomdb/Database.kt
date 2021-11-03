package com.example.e_commerceapplication.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.e_commerceapplication.roomdb.address.AddressDao
import com.example.e_commerceapplication.roomdb.address.AddressEntity
import com.example.e_commerceapplication.roomdb.cart.CartDao
import com.example.e_commerceapplication.roomdb.cart.CartEntity
import com.example.e_commerceapplication.roomdb.product.ProductDao
import com.example.e_commerceapplication.roomdb.product.ProductEntity

@Database(entities = [UserEntity::class, ProductEntity::class, CartEntity::class, AddressEntity::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun registerDatabaseDao(): UserDao
    abstract fun registerData(): ProductDao
    abstract fun registerDataCart(): CartDao
    abstract fun registerDataAddress(): AddressDao
}