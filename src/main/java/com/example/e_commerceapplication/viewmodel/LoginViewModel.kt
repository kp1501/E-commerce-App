package com.example.e_commerceapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.e_commerceapplication.repository.RegisterRepository
import com.example.e_commerceapplication.roomdb.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: RegisterRepository): ViewModel() {

    fun getUser(entity: UserEntity) = repository.getUser(entity)

    fun getId(entity: UserEntity) = repository.getId(entity)
}
