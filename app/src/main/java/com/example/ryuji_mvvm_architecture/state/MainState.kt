package com.example.ryuji_mvvm_architecture.state

import androidx.fragment.app.Fragment
import com.example.ryuji_mvvm_architecture.view.fragment.FirstFragment
import com.example.ryuji_mvvm_architecture.view.fragment.SecondFragment
import com.example.ryuji_mvvm_architecture.view.fragment.ThirdFragment

// region ParentScreenState
enum class ParentScreenState(val fragment: Fragment, val title: String, val progress: Int) : ScreenState {
    FIRST(FirstFragment(), "1/3", 33),
    SECOND(SecondFragment(), "2/3", 66),
    THIRD(ThirdFragment(), "3/3", 100);
}
// endregion

// region First
enum class FirstScreenState : ScreenState {
    INITIALIZE,
    INITIALIZED,
    FETCH,
    FETCHED,
    NEXT
}

data class FirstData(val text: String = "DEFAULT")
data class FirstState(val screenState: FirstScreenState, val data: FirstData)
// endregion

// region Second
enum class SecondScreenState : ScreenState {
    FETCH_FROM_SERVER,
    LOADING,
    FETCHED,
    NEXT
}

data class SecondData(val text: String = "DEFAULT")
data class SecondState(val screenState: SecondScreenState, val data: SecondData)
// endregion

// region Third
enum class ThirdScreenState : ScreenState {
    BACK
}

data class ThirdData(val text: String = "DEFAULT")
data class ThirdState(val screenState: ThirdScreenState, val data: ThirdData)
// endregion