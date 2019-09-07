package com.example.ryuji_mvvm_architecture.state

import androidx.fragment.app.Fragment
import com.example.ryuji_mvvm_architecture.view.fragment.FirstFragment
import com.example.ryuji_mvvm_architecture.view.fragment.SecondFragment
import com.example.ryuji_mvvm_architecture.view.fragment.ThirdFragment

// region MainTransitionState
enum class MainTransitionState(val fragment: Fragment?) {
    FIRST(FirstFragment()),
    SECOND(SecondFragment()),
    THIRD(ThirdFragment()),
    BACK(null)
}
// endregion

// region First
enum class FirstScreenState : ScreenState {
    INITIALIZE,
    FETCH,
    LOADING,
    FINISH
}

data class FirstData(val text: String)
data class FirstState(val screenState: FirstScreenState, val data: FirstData)
// endregion

// region Second
enum class SecondScreenState : ScreenState {
    INITIALIZE,
    FETCH,
    LOADING,
    FINISH
}

data class SecondData(val text: String)
data class SecondState(val screenState: SecondScreenState, val data: SecondData)
// endregion

// region Third
enum class ThirdScreenState : ScreenState {
    INITIALIZE,
    FETCH,
    LOADING,
    FINISH
}

data class ThirdData(val text: String)
data class ThirdState(val screenState: ThirdScreenState, val data: ThirdData)
// endregion