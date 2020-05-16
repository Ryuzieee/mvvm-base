package com.example.ryuji_mvvm_architecture.base

import androidx.fragment.app.Fragment

interface State

interface ActionState : State {
    fun id(): String
}

interface DispatchState : State {
    fun id(): String
    fun isAcceptAction(): Boolean
}

interface ScreenState : State

interface TransitionState : ScreenState {
    val fragment: Fragment
}

interface FragmentScreenState : ScreenState {
    fun id(): String
}

interface Data

interface ActionData : Data

interface DispatchData : Data

interface ScreenData : Data

interface Property {
    val fragmentScreenState: FragmentScreenState
    val actionData: ActionData
    val dispatchData: DispatchData
    val dispatchState: DispatchState
    val screenData: ScreenData
    fun createNewProperty(state: State, data: Data?): Property
}