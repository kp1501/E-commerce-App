package com.example.e_commerceapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapplication.repository.RegisterRepository
import com.example.e_commerceapplication.roomdb.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository): ViewModel() {

    fun insert(entity: UserEntity) = viewModelScope.launch {
        repository.insert(entity)
    }
}

