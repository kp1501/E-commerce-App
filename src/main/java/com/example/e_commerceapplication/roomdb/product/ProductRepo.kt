package com.example.e_commerceapplication.roomdb.product

import androidx.annotation.WorkerThread
import com.example.e_commerceapplication.roomdb.Database
import com.example.e_commerceapplication.roomdb.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepo @Inject constructor(appDb: Database) {

    private val productDao = appDb.registerData()

    fun allProduct(productEntity: ProductEntity): Flow<List<ProductEntity>> {
        return productDao.fetchProductList(productEntity.product_userid)
    }
//    val allProduct: Flow<List<ProductEntity>> = productDao.fetchProductList()

    fun allWishList(productEntity: ProductEntity): Flow<List<ProductEntity>> {
        return productDao.fetchWishList(productEntity.product_save.toString(),productEntity.product_userid)
    }

    fun getProductSave(productEntity: ProductEntity): Int{
        return productDao.getProductSave(productEntity.productId,productEntity.product_userid)
    }

    @WorkerThread
    suspend fun insertProduct(productEntity: ProductEntity) {
        productDao.insertProduct(productEntity)
    }

    @WorkerThread
    suspend fun insertList(productEntity: List<ProductEntity>) {
        productDao.insertList(productEntity)
    }

    @WorkerThread
    suspend fun updateProduct(productEntity: ProductEntity) {
        productDao.updateProduct(productEntity)
    }
}