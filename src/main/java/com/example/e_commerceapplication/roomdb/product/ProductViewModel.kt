package com.example.e_commerceapplication.roomdb.product

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepo: ProductRepo) : ViewModel() {

    fun allProducts(productEntity: ProductEntity) = productRepo.allProduct(productEntity).asLiveData()

    fun allWishList(productEntity: ProductEntity) = productRepo.allWishList((productEntity)).asLiveData()


    fun insertProduct(productEntity: ProductEntity) = viewModelScope.launch {
        productRepo.insertProduct(productEntity)
    }

    fun updateProduct(productEntity: ProductEntity) = viewModelScope.launch {
        productRepo.updateProduct(productEntity)
    }
}