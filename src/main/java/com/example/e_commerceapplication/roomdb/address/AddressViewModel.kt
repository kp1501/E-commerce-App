package com.example.e_commerceapplication.roomdb.address

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val addressRepo: AddressRepo): ViewModel() {

    val allAddress: LiveData<List<AddressEntity>> = addressRepo.allAddress.asLiveData()

    fun insertAddress(addressEntity: AddressEntity) = viewModelScope.launch {
        addressRepo.insertAddress(addressEntity)
    }

    fun deleteAddress(addressEntity: AddressEntity) = viewModelScope.launch {
       addressRepo.deleteAddress(addressEntity)
    }
}