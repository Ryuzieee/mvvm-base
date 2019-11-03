package com.example.ryuji_mvvm_architecture.base

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ryuji_mvvm_architecture.main.MainTransitionState

abstract class BaseViewModel : ViewModel() {

    private val log = mutableListOf<String>()

    abstract val transitionState: MutableLiveData<TransitionState>

    abstract val transitionStateList: List<TransitionState>

    abstract val propertyMap: Map<String, MutableLiveData<out Property>>

    abstract val functionMap: Map<ScreenState, ((Any?) -> Unit)?>

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
                fragmentScreenState = fragmentScreenState,
                dispatchData = if (any is Data) any else null
            )
            propertyMap[fragmentScreenState.id()]?.value = newProperty
            functionMap[fragmentScreenState]?.let { it(any) }
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
        val current = MainTransitionState.values().indexOf(transitionState.value)
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
}