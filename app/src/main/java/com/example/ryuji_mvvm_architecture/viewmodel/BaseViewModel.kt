package com.example.ryuji_mvvm_architecture.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ryuji_mvvm_architecture.state.ScreenState

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    abstract fun dispatch(state: ScreenState, dispatchData: Any? = null)
}