package com.example.ryuji_mvvm_architecture.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.model.MainProvider

class MainViewModel(application: Application) : BaseViewModel(application) {

    private var data: MutableLiveData<String> = MutableLiveData()

    fun fetchText(): MutableLiveData<String> {
        if (data.value.isNullOrEmpty()) loadText()
        return data
    }

    private fun loadText() {
        data.value = MainProvider().fetchText()
    }
}