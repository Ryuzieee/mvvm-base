package com.example.ryuji_mvvm_architecture.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: 本当はAnyじゃなく何かStateをとるようにする!!
    abstract fun dispatch(state: Any)
}