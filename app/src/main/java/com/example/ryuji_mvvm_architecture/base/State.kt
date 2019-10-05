package com.example.ryuji_mvvm_architecture.base

import androidx.fragment.app.Fragment

interface ScreenState

interface Data

interface TransitionState : ScreenState {
    val fragment: Fragment
}

interface Property {
    val screenState: ScreenState
    val data: Data
}