package com.example.ryuji_mvvm_architecture.main

class MainProviderImpl : MainProvider {
    override fun request(): String {
        return "FETCHED"
    }
}