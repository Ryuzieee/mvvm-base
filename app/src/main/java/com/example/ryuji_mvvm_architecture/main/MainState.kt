package com.example.ryuji_mvvm_architecture.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.ryuji_mvvm_architecture.base.Data
import com.example.ryuji_mvvm_architecture.base.Property
import com.example.ryuji_mvvm_architecture.base.ScreenState
import com.example.ryuji_mvvm_architecture.base.TransitionState
import com.example.ryuji_mvvm_architecture.main.fragment.FirstFragment
import com.example.ryuji_mvvm_architecture.main.fragment.SecondFragment
import com.example.ryuji_mvvm_architecture.main.fragment.ThirdFragment

// region MainTransitionState
enum class MainTransitionState(override val fragment: Fragment, val title: String, val progress: Int) :
    TransitionState {
    FIRST(FirstFragment(), "1/3", 33),
    SECOND(SecondFragment(), "2/3", 66),
    THIRD(ThirdFragment(), "3/3", 100);

    fun isFirstFragment() = fragment == FIRST.fragment
}
// endregion

// region First
enum class FirstScreenState : ScreenState {
    INITIAL,
    NEXT
}

data class FirstData(val text: String = "DEFAULT") : Data

data class FirstProperty(override val screenState: FirstScreenState, override val data: FirstData) : Property {
    override fun updateProperty(screenState: ScreenState, dispatchData: Data?): Property {
        return this.copy(
            screenState = screenState as FirstScreenState,
            data = dispatchData?.let { it as FirstData } ?: data)
    }
}
// endregion

// region Second
enum class SecondScreenState : ScreenState {
    INITIAL,
    LOADING,
    FETCHED,
    NEXT
}

data class SecondData(val text: String = "DEFAULT") : Data

data class SecondProperty(override val screenState: SecondScreenState, override val data: SecondData) : Property {
    override fun updateProperty(screenState: ScreenState, dispatchData: Data?): Property {
        return this.copy(
            screenState = screenState as SecondScreenState,
            data = dispatchData?.let { it as SecondData } ?: data)
    }

    fun isLoading(): Boolean {
        return when (screenState) {
            SecondScreenState.INITIAL, SecondScreenState.LOADING -> true
            SecondScreenState.FETCHED, SecondScreenState.NEXT -> false
        }
    }
}
// endregion

// region Third
enum class ThirdScreenState : ScreenState {
    INITIAL,
    BACK
}

data class ThirdData(
    val username: MutableLiveData<String> = MutableLiveData(),
    val password: MutableLiveData<String> = MutableLiveData(),
    val termOfUse: MutableLiveData<Boolean> = MutableLiveData(),
    val canSubmit: MediatorLiveData<Boolean> = MediatorLiveData()
) : Data {
    fun isValid() = !username.value.isNullOrBlank() && !password.value.isNullOrBlank() && termOfUse.value == true
}

data class ThirdProperty(override val screenState: ThirdScreenState, override val data: ThirdData) : Property {
    override fun updateProperty(screenState: ScreenState, dispatchData: Data?): Property {
        return this.copy(
            screenState = screenState as ThirdScreenState,
            data = dispatchData?.let { it as ThirdData } ?: data)
    }
}

// endregion