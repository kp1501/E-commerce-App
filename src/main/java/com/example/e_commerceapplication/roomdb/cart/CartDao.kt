package com.example.e_commerceapplication.roomdb.cart

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCart(cartEntity: CartEntity?)

    @Query("SELECT * FROM cart where cart_userid= :userId ORDER BY cart_name")
    fun fetchCartList(userId: Int): Flow<List<CartEntity>>

    @Delete
    suspend fun deleteCart(cartEntity: CartEntity?)

    @Update
    suspend fun updateCart(cartEntity: CartEntity?)
}
