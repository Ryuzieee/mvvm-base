package com.example.ryuji_mvvm_architecture.base

import androidx.fragment.app.Fragment

interface ScreenState

interface TransitionState : ScreenState {
    val fragment: Fragment
}

interface FragmentScreenState : ScreenState {
    fun id(): String
}

interface Data

interface Property {
    val screenState: FragmentScreenState
    val data: Data
    fun createNewProperty(screenState: FragmentScreenState, data: Data?): Property
}