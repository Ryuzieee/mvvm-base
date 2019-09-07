package com.example.ryuji_mvvm_architecture.model

interface Provider {
    fun request(requestData: Any? = null): Any?
}