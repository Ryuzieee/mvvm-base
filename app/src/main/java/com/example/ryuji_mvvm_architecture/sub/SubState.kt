package com.example.ryuji_mvvm_architecture.sub

import androidx.fragment.app.Fragment
import com.example.ryuji_mvvm_architecture.base.Data
import com.example.ryuji_mvvm_architecture.base.FragmentScreenState
import com.example.ryuji_mvvm_architecture.base.Property
import com.example.ryuji_mvvm_architecture.base.TransitionState

// region SubTransitionState
enum class SubTransitionState(override val fragment: Fragment) :
    TransitionState {
    SUB(SubFragment());

    fun isFirstFragment() = fragment == SUB.fragment
}
// endregion

// region SUB
enum class SubScreenState : FragmentScreenState {
    INITIAL;

    override fun id(): String = javaClass.simpleName
}

data class SubData(val text: String = "DEFAULT") : Data

data class SubProperty(override val screenState: SubScreenState, override val data: SubData) : Property {
    override fun createNewProperty(screenState: FragmentScreenState, data: Data?): Property {
        return this.copy(
            screenState = screenState as SubScreenState,
            data = data?.let { it as SubData } ?: this.data)
    }
}
// endregion