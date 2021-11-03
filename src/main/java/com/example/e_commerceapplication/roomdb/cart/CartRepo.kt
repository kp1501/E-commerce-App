package com.example.e_commerceapplication.roomdb.cart

import androidx.annotation.WorkerThread
import com.example.e_commerceapplication.roomdb.Database
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepo @Inject constructor(appDb: Database) {

    private val cartDao = appDb.registerDataCart()

    fun allProduct(cartEntity: CartEntity): Flow<List<CartEntity>> {
        return cartDao.fetchCartList(cartEntity.cart_userid)
    }
//    val allProduct: Flow<List<CartEntity>> = cartDao.fetchCartList()

    @WorkerThread
    suspend fun insertCart(cartEntity: CartEntity) {
        cartDao.insertCart(cartEntity)
    }

    @WorkerThread
    suspend fun deleteCart(cartEntity: CartEntity) {
        cartDao.deleteCart(cartEntity)
    }

    @WorkerThread
    suspend fun updateCart(cartEntity: CartEntity) {
        cartDao.updateCart(cartEntity)
    }
}