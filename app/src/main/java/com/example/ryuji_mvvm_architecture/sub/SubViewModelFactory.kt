package com.example.ryuji_mvvm_architecture.sub

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SubViewModelFactory :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == SubViewModel::class.java)
            return SubViewModel() as T

        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }
}