package com.example.ryuji_mvvm_architecture.main

import androidx.fragment.app.Fragment
import com.example.ryuji_mvvm_architecture.base.*
import com.example.ryuji_mvvm_architecture.main.view.fragment.FirstFragment
import com.example.ryuji_mvvm_architecture.main.view.fragment.SecondFragment

// region MainTransitionState

enum class MainTransitionState(override val fragment: Fragment) :
    TransitionState {
    FIRST(FirstFragment()),
    SECOND(SecondFragment()),
    ;
}

// endregion

// region First

internal const val PROPERTY_ID_FIRST = "property_id_first"

enum class FirstActionState : ActionState {
    ON_INITIALIZE,
    ON_TAPPED_NEXT,
    ;

    override fun id(): String = PROPERTY_ID_FIRST
}

enum class FirstDispatchState : DispatchState {
    USER_ACTION_WAITING,
    INITIALIZE,
    NEXT,
    ;

    override fun id(): String = PROPERTY_ID_FIRST
    override fun isAcceptAction(): Boolean {
        return this == USER_ACTION_WAITING
    }
}

enum class FirstFragmentScreenState : FragmentScreenState {
    NONE,
    INITIALIZED
    ;

    override fun id(): String = PROPERTY_ID_FIRST
}

data class FirstActionData(val text: String = "") : ActionData

data class FirstDispatchData(val text: String = "") : DispatchData

data class FirstScreenData(val text: String = "NEXT") : ScreenData

data class FirstProperty(
    override val actionData: FirstActionData,
    override val dispatchData: FirstDispatchData,
    override val screenData: FirstScreenData,
    override val dispatchState: DispatchState,
    override val fragmentScreenState: FirstFragmentScreenState
) : Property {
    override fun createNewProperty(state: State, data: Data?): Property {
        return when (state) {
            is FirstActionState -> data?.let { this.copy(actionData = it as FirstActionData) } ?: this
            is FirstDispatchState -> this.copy(
                dispatchState = state,
                dispatchData = data?.let { it as FirstDispatchData } ?: this.dispatchData
            )
            is FirstFragmentScreenState -> this.copy(
                fragmentScreenState = state,
                screenData = data?.let { it as FirstScreenData } ?: this.screenData
            )
            else -> this.copy()
        }
    }
}

// endregion

// region Second

internal const val PROPERTY_ID_SECOND = "property_id_second"

enum class SecondActionState : ActionState {
    ON_INITIALIZE,
    ON_TAPPED_BACK
    ;

    override fun id(): String = PROPERTY_ID_SECOND
}

enum class SecondDispatchState : DispatchState {
    USER_ACTION_WAITING,
    INITIALIZE,
    BACK,
    ;

    override fun id(): String = PROPERTY_ID_SECOND
    override fun isAcceptAction(): Boolean {
        return this == USER_ACTION_WAITING
    }
}

enum class SecondFragmentScreenState : FragmentScreenState {
    NONE,
    INITIALIZED
    ;

    override fun id(): String = PROPERTY_ID_SECOND
}

data class SecondActionData(val text: String = "") : ActionData

data class SecondDispatchData(val text: String = "") : DispatchData

data class SecondScreenData(val text: String = "BACK") : ScreenData

data class SecondProperty(
    override val actionData: SecondActionData,
    override val dispatchData: SecondDispatchData,
    override val screenData: SecondScreenData,
    override val dispatchState: DispatchState,
    override val fragmentScreenState: SecondFragmentScreenState
) : Property {
    override fun createNewProperty(state: State, data: Data?): Property {
        return when (state) {
            is SecondActionState -> data?.let { this.copy(actionData = it as SecondActionData) } ?: this
            is SecondDispatchState -> this.copy(
                dispatchState = state,
                dispatchData = data?.let { it as SecondDispatchData } ?: this.dispatchData
            )
            is SecondFragmentScreenState -> this.copy(
                fragmentScreenState = state,
                screenData = data?.let { it as SecondScreenData } ?: this.screenData
            )
            else -> this.copy()
        }
    }
}

// endregion