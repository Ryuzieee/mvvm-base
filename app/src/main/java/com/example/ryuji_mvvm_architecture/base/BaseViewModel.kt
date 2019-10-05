package com.example.ryuji_mvvm_architecture.base

import android.app.Application
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ryuji_mvvm_architecture.main.ParentScreenState

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    abstract val transitionState: MutableLiveData<TransitionState>

    abstract val transitionStateList: List<TransitionState>

    abstract val propertyList: List<MutableLiveData<out Property>>

    abstract val functionMap: Map<ScreenState, (() -> Unit)?>

    open fun dispatch(screenState: ScreenState, dispatchData: Any? = null) {
        when (screenState) {
            is TransitionState -> {
                transitionState.value = screenState
            }
            else -> {
                propertyList.forEachIndexed { index, mutableLiveData ->
                    // Propertyの更新
                    if (mutableLiveData.value?.screenState?.javaClass == screenState.javaClass) {
                        val newProperty = propertyList[index].value?.makeNewProperty(
                            screenState = screenState,
                            dispatchData = if (dispatchData is Data) dispatchData else null
                        )
                        propertyList[index].value = newProperty
                    }
                }
                functionMap[screenState]?.let { it() }
            }
        }
    }

    open fun nextTransitionState(): TransitionState? {
        val current = transitionStateList.indexOf(transitionState.value)
        // 現在のindexがMAX未満である場合、現在の表示を最終ページ以外であると見なし、indexを+1したページを返却する
        return if (current < transitionStateList.lastIndex) {
            transitionStateList[current + 1]
        } else {
            null
        }
    }

    open fun previousTransitionState(): TransitionState? {
        val current = ParentScreenState.values().indexOf(transitionState.value)
        // 現在のindexが1以上である場合、現在の表示を2ページ目以降と見なし、indexを-1したページを返却する
        return if (current > 0) {
            transitionStateList[current - 1]
        } else {
            null
        }
    }

    open fun isBack(supportFragmentManager: FragmentManager): Boolean {
        // TransitionStateのindexが現在のStateよりも小さい場合は画面を戻ると判断しback
        val new = transitionStateList.indexOf(transitionState.value)
        val old = transitionStateList.indexOfFirst {
            it.fragment == supportFragmentManager.fragments.first()
        }
        return old > new
    }
}