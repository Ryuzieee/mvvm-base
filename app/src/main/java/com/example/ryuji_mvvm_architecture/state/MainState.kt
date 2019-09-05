package com.example.ryuji_mvvm_architecture.state

import androidx.fragment.app.Fragment
import com.example.ryuji_mvvm_architecture.view.fragment.MainFragment
import com.example.ryuji_mvvm_architecture.view.fragment.SecondFragment

// region MainTransitionState
enum class MainTransitionState(val fragment: Fragment) {
    MAIN(MainFragment()),
    SECOND(SecondFragment())
}
// endregion

// region Main
enum class MainScreenState {
    INITIALIZE,
    FETCH,
    LOADING,
    FINISH
}
data class MainData(val text: String)
data class MainState(val screenState: MainScreenState, val data: MainData)
// endregion

// region Second
enum class SecondScreenState {
    INITIALIZE,
    FETCH,
    LOADING,
    FINISH
}
data class SecondData(val text: String)
data class SecondState(val screenState: SecondScreenState, val data: SecondData)
// endregion