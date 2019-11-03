package com.example.ryuji_mvvm_architecture.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ryuji_mvvm_architecture.main.provider.MainProvider

class MainViewModelFactory
constructor(private val provider: MainProvider) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == MainViewModel::class.java)
            return MainViewModel(provider) as T

        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }
}