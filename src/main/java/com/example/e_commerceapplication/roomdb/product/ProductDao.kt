package com.example.e_commerceapplication.roomdb.product

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(productEntity: ProductEntity?)

    @Insert
    suspend fun insertList(productEntity: List<ProductEntity>)

    @Update
    suspend fun updateProduct(productEntity: ProductEntity?)

    @Query("SELECT * FROM product where product_userid= :userId ORDER BY product_name")
    fun fetchProductList(userId: Int): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product where product_save= :save and product_userid= :userId ORDER BY product_name")
    fun fetchWishList(save: String, userId: Int): Flow<List<ProductEntity>>

    @Query("SELECT product_save FROM product where productId= :productId and product_userid= :userId")
    fun getProductSave(productId: Int?, userId: Int): Int

}
