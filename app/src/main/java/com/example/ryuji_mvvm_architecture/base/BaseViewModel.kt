package com.example.ryuji_mvvm_architecture.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    // region メンバ変数(初期化必須)

    abstract val transitionState: MutableLiveData<TransitionState>

    abstract val transitionStateList: List<TransitionState>

    abstract val propertyMap: Map<String, MutableLiveData<out Property>>

    abstract val actionMap: Map<ActionState, (() -> Unit)?>

    abstract val dispatchMap: Map<DispatchState, (() -> Unit)?>

    // endregion

    // region 公開するメンバ関数

    internal fun onAction(actionState: ActionState, actionData: ActionData? = null) {

        val dispatchState = this.propertyMap[actionState.id()]?.value?.dispatchState ?: return
        if (!dispatchState.isAcceptAction()) return

        Log.d("LOG", actionState.javaClass.simpleName + "." + actionState)

        // Propertyの更新
        this.propertyMap[actionState.id()]?.value?.let { property ->
            val newProperty = property.createNewProperty(state = actionState, data = actionData)
            this.propertyMap[actionState.id()]?.value = newProperty
        }

        // Actionの実行
        this.actionMap[actionState]?.let { it() }

    }

    // endregion

    // region 公開しないメンバ関数

    protected fun onDispatch(dispatchState: DispatchState, dispatchData: DispatchData? = null) {

        Log.d("LOG", dispatchState.javaClass.simpleName + "." + dispatchState)

        // Propertyの更新
        this.propertyMap[dispatchState.id()]?.value?.let { property ->
            val newProperty = property.createNewProperty(state = dispatchState, data = dispatchData)
            this.propertyMap[dispatchState.id()]?.value = newProperty
        }

        // dispatchの実行
        this.dispatchMap[dispatchState]?.let { it() }

    }

    protected fun onChangeScreen(screenState: ScreenState, screenData: ScreenData? = null) {

        Log.d("LOG", screenState.javaClass.simpleName + "." + screenState)

        // TransitionStateを更新してリターン
        if (screenState is TransitionState) {
            this.transitionState.value = screenState
            return
        }

        // Propertyの更新
        val fragmentScreenState = screenState as FragmentScreenState
        this.propertyMap[fragmentScreenState.id()]?.value?.let { property ->
            val newProperty = property.createNewProperty(state = fragmentScreenState, data = screenData)
            this.propertyMap[fragmentScreenState.id()]?.value = newProperty
        }

    }

    protected fun <T> getProperty(id: String): MutableLiveData<T>? = propertyMap[id] as MutableLiveData<T>?

    // endregion

}