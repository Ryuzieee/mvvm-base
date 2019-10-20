package com.example.ryuji_mvvm_architecture.main

import com.example.ryuji_mvvm_architecture.base.Provider

class MainProvider : Provider {
    override fun request(): String {
        return "FETCHED"
    }
}