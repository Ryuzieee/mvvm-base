package com.example.ryuji_mvvm_architecture.base

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    // region メンバ変数(初期化必須)

    abstract val transitionState: MutableLiveData<TransitionState>

    abstract val transitionStateList: List<TransitionState>

    abstract val propertyMap: Map<String, MutableLiveData<out Property>>

    abstract val businessLogicMap: Map<ScreenState, ((Any?) -> Unit)?>

    // endregion

    // region メンバ変数

    private val log = mutableListOf<String>()

    // endregion

    // region 公開するメンバ関数

    internal fun dispatch(screenState: ScreenState, any: Any? = null) {

        // ScreenStateのログを収集
        log.add(screenState.javaClass.simpleName + "." + screenState)

        // TransitionStateを更新してリターン
        if (screenState is TransitionState) {
            transitionState.value = screenState
            return
        }

        // Propertyを更新
        val fragmentScreenState = screenState as FragmentScreenState
        propertyMap[fragmentScreenState.id()]?.value?.let { property ->
            val newProperty = property.createNewProperty(
                screenState = fragmentScreenState,
                data = if (any is Data) any else null
            )
            propertyMap[fragmentScreenState.id()]?.value = newProperty
            businessLogicMap[fragmentScreenState]?.let { it(any) }
        }

    }

    // 1つ次のTransitionStateを返却する
    internal fun nextTransitionState(): TransitionState? {
        val current = transitionStateList.indexOf(transitionState.value)
        return if (current < transitionStateList.lastIndex) {
            transitionStateList[current + 1]
        } else {
            null
        }
    }

    // 1つ前のTransitionStateを返却する
    internal fun previousTransitionState(): TransitionState? {
        val current = transitionStateList.indexOf(transitionState.value)
        return if (current > 0) {
            transitionStateList[current - 1]
        } else {
            null
        }
    }

    // 1つ前のTransitionStateに戻れるか否かを返却する
    internal fun isBack(supportFragmentManager: FragmentManager): Boolean {
        val new = transitionStateList.indexOf(transitionState.value)
        val old = transitionStateList.indexOfFirst {
            it.fragment == supportFragmentManager.fragments.first()
        }
        return old > new
    }

    // endregion

}