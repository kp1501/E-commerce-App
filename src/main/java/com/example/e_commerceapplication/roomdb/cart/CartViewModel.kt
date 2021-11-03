package com.example.e_commerceapplication.roomdb.cart

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartRepo: CartRepo) : ViewModel() {

    fun allProducts(cartEntity: CartEntity) = cartRepo.allProduct(cartEntity).asLiveData()

    fun insertProduct(cartEntity: CartEntity) = viewModelScope.launch {
        cartRepo.insertCart(cartEntity)
    }

    fun deleteCart(cartEntity: CartEntity) = viewModelScope.launch {
        cartRepo.deleteCart(cartEntity)
    }

    fun updateCart(cartEntity: CartEntity) = viewModelScope.launch {
        cartRepo.updateCart(cartEntity)
    }
}