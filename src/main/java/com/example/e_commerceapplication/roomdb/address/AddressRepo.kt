package com.example.e_commerceapplication.roomdb.address

import androidx.annotation.WorkerThread
import com.example.e_commerceapplication.roomdb.Database
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRepo @Inject constructor (appDb: Database) {

    private val addressDao = appDb.registerDataAddress()

    val allAddress: Flow<List<AddressEntity>> = addressDao.fetchAddressList()

    @WorkerThread
    suspend fun insertAddress(addressEntity: AddressEntity) {
        addressDao.insertAddress(addressEntity)
    }

    @WorkerThread
    suspend fun deleteAddress(addressEntity: AddressEntity) {
        addressDao.deleteAddress(addressEntity)
    }
}