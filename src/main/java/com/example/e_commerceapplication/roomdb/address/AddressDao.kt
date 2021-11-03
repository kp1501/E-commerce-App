package com.example.e_commerceapplication.roomdb.address

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAddress(addressEntity: AddressEntity?)

    @Query("SELECT * FROM address ORDER BY addressId")
    fun fetchAddressList(): Flow<List<AddressEntity>>

    @Delete
    suspend fun deleteAddress(addressEntity: AddressEntity?)

}
