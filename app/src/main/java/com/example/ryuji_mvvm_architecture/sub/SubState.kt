package com.example.ryuji_mvvm_architecture.sub

import androidx.fragment.app.Fragment
import com.example.ryuji_mvvm_architecture.base.Data
import com.example.ryuji_mvvm_architecture.base.FragmentScreenState
import com.example.ryuji_mvvm_architecture.base.Property
import com.example.ryuji_mvvm_architecture.base.TransitionState

// region MainTransitionState
enum class SubTransitionState(override val fragment: Fragment) :
    TransitionState {
    SUB(SubFragment());

    fun isFirstFragment() = fragment == SUB.fragment
}
// endregion

// region SUB
enum class SubScreenState : FragmentScreenState {
    INITIAL,
    NEXT;

    override fun id() = javaClass.simpleName
}

data class SubData(val text: String = "DEFAULT") : Data

data class SubProperty(override val fragmentScreenState: SubScreenState, override val data: SubData) : Property {
    override fun createNewProperty(fragmentScreenState: FragmentScreenState, dispatchData: Data?): Property {
        return this.copy(
            fragmentScreenState = fragmentScreenState as SubScreenState,
            data = dispatchData?.let { it as SubData } ?: data)
    }
}
// endregion