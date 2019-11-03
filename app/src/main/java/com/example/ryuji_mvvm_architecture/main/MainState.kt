package com.example.ryuji_mvvm_architecture.main

import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.ryuji_mvvm_architecture.base.*
import com.example.ryuji_mvvm_architecture.main.view.fragment.FirstFragment
import com.example.ryuji_mvvm_architecture.main.view.fragment.SecondFragment
import com.example.ryuji_mvvm_architecture.main.view.fragment.ThirdFragment

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
enum class FirstScreenState : FragmentScreenState {
    INITIAL,
    NEXT;

    override fun id(): String = javaClass.simpleName
}

data class FirstData(val text: String = "DEFAULT") : Data

data class FirstProperty(override val screenState: FirstScreenState, override val data: FirstData) : Property {
    override fun createNewProperty(screenState: FragmentScreenState, data: Data?): Property {
        return this.copy(
            screenState = screenState as FirstScreenState,
            data = data?.let { it as FirstData } ?: this.data)
    }
}
// endregion

// region Second
enum class SecondScreenState : FragmentScreenState {
    INITIAL,
    LOADING,
    FETCHED,
    NEXT;

    override fun id(): String = javaClass.simpleName
}

data class SecondData(val text: String = "DEFAULT") : Data

data class SecondProperty(override val screenState: SecondScreenState, override val data: SecondData) :
    Property {
    override fun createNewProperty(screenState: FragmentScreenState, data: Data?): Property {
        return this.copy(
            screenState = screenState as SecondScreenState,
            data = data?.let { it as SecondData } ?: this.data)
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
enum class ThirdScreenState : ScreenState, FragmentScreenState {
    INITIAL,
    START_NEXT_ACTIVITY;

    override fun id(): String = javaClass.simpleName
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
    override fun createNewProperty(screenState: FragmentScreenState, data: Data?): Property {
        return this.copy(
            screenState = screenState as ThirdScreenState,
            data = data?.let { it as ThirdData } ?: this.data)
    }
}

// endregion