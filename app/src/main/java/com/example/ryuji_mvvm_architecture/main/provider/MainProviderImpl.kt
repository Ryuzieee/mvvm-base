package com.example.ryuji_mvvm_architecture.main.provider

class MainProviderImpl : MainProvider {
    override fun request(): String {
        return "FETCHED"
    }
}