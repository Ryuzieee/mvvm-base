package com.example.ryuji_mvvm_architecture.base

import androidx.fragment.app.Fragment

interface ScreenState

interface Data

interface TransitionState : ScreenState {
    val fragment: Fragment
}

interface FragmentScreenState : ScreenState {
    fun id(): String
}

interface Property {
    val fragmentScreenState: FragmentScreenState
    val data: Data
    fun createNewProperty(fragmentScreenState: FragmentScreenState, dispatchData: Data?): Property
}