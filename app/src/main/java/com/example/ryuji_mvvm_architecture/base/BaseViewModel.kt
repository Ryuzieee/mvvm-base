package com.example.ryuji_mvvm_architecture.base

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

    /**
     * FIXME:以下の3つに分ける
     *  ①onAction(ユーザー操作の受付)
     *  (ViewModel)actionMap: Map<ActionState, (() -> Unit)?>
     *  ②onDispatch(ビジネスロジックの受付)
     *  (ViewModel)dispatchMap: Map<dispatchState, (() -> Unit)?>
     *  ③onChangeScreen(ユーザーへのフィードバック)
     *  (View)screenMap: Map<ScreenState, (() -> Unit)?>
     *  その際全てのタイミングでpropertyを更新する!!
     *  ※懸念としては上記の3つのタイミングでdataを受け渡す際、
     *  必ず最新のproperty(dataだけでいいか)をcopyして情報を上書きする形になる
     *  そうなると、onAction,onDispatchで使用するdataは必ずしもscreenStateで使用するdataではなくなる
     *  propertyの構造を以下のようにすればいいかも。
     *  (現状)screenState: FragmentScreenState,data: Data
     *  (改良)screenState: FragmentScreenState,actionData: Data, dispatchData: Data, screenData: Data
     */
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

    // endregion

}