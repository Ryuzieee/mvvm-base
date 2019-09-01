package com.example.ryuji_mvvm_architecture.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ryuji_mvvm_architecture.model.MainProvider

class MainViewModel : ViewModel() {

    var liveDataText: MutableLiveData<String> = MutableLiveData()

    fun fetchText() {
        liveDataText.value = MainProvider().fetchText()
    }
}