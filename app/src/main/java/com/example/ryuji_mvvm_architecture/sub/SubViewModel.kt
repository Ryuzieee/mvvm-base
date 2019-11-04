package com.example.ryuji_mvvm_architecture.sub

import androidx.lifecycle.MutableLiveData
import com.example.ryuji_mvvm_architecture.base.BaseViewModel
import com.example.ryuji_mvvm_architecture.base.Property
import com.example.ryuji_mvvm_architecture.base.ScreenState
import com.example.ryuji_mvvm_architecture.base.TransitionState

class SubViewModel : BaseViewModel() {

    // region メンバ変数(初期化必須)

    override val transitionState: MutableLiveData<TransitionState> = MutableLiveData(SubTransitionState.SUB)

    override val transitionStateList = SubTransitionState.values().asList()

    override val propertyMap: Map<String, MutableLiveData<out Property>> = mapOf(
        SubScreenState.values().first().id() to MutableLiveData<SubProperty>(
            SubProperty(SubScreenState.INITIAL, SubData())
        )
    )

    override val businessLogicMap: Map<ScreenState, ((Any?) -> Unit)?> = mapOf(
        SubScreenState.INITIAL to { _ -> null }
    )

    // endregion

}