package com.example.ryuji_mvvm_architecture.main.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.ryuji_mvvm_architecture.base.*
import com.example.ryuji_mvvm_architecture.main.*
import com.example.ryuji_mvvm_architecture.main.provider.MainProvider

class MainViewModel(private val mProvider: MainProvider) : BaseViewModel() {

    // region メンバ変数(初期化必須)

    override val transitionState: MutableLiveData<TransitionState> = MutableLiveData(MainTransitionState.FIRST)

    override val transitionStateList = MainTransitionState.values().asList()

    override val propertyMap: Map<String, MutableLiveData<out Property>> = mapOf(
        PROPERTY_ID_FIRST to MutableLiveData<FirstProperty>(
            FirstProperty(
                FirstActionData(),
                FirstDispatchData(),
                FirstScreenData(),
                FirstDispatchState.USER_ACTION_WAITING,
                FirstFragmentScreenState.NONE
            )
        ),
        PROPERTY_ID_SECOND to MutableLiveData<SecondProperty>(
            SecondProperty(
                SecondActionData(),
                SecondDispatchData(),
                SecondScreenData(),
                SecondDispatchState.USER_ACTION_WAITING,
                SecondFragmentScreenState.NONE
            )
        )
    )

    override val actionMap: Map<ActionState, (() -> Unit)?> = mapOf(

        ////////// First //////////

        FirstActionState.ON_INITIALIZE to ::requestFirstInitialize,
        FirstActionState.ON_TAPPED_NEXT to ::requestNext,

        ////////// Second //////////

        SecondActionState.ON_INITIALIZE to ::requestSecondInitialize,
        SecondActionState.ON_TAPPED_BACK to ::requestBack

    )

    override val dispatchMap: Map<DispatchState, (() -> Unit)?> = mapOf(

        ////////// First //////////

        FirstDispatchState.INITIALIZE to ::firstInitialize,
        FirstDispatchState.NEXT to ::next,

        ////////// First //////////

        SecondDispatchState.INITIALIZE to ::secondInitialize,
        SecondDispatchState.BACK to ::back
    )

    // endregion

    // region メンバ変数

    private val mFirstProperty = this.getProperty<FirstProperty>(
        PROPERTY_ID_FIRST
    )

    private val mSecondProperty = this.getProperty<SecondProperty>(
        PROPERTY_ID_SECOND
    )

    // endregion

    // region First メンバ関数(From Action)

    private fun requestFirstInitialize() = this.onDispatch(FirstDispatchState.INITIALIZE)

    private fun requestNext() = this.onDispatch(FirstDispatchState.NEXT)

    private fun firstInitialize() {
        this.onDispatch(FirstDispatchState.USER_ACTION_WAITING)
        this.onChangeScreen(FirstFragmentScreenState.INITIALIZED)
    }

    private fun next() {
        this.onDispatch(FirstDispatchState.USER_ACTION_WAITING)
        this.onChangeScreen(MainTransitionState.SECOND)
    }

    // endregion

    // region Second メンバ関数(From Action)

    private fun requestSecondInitialize() = this.onDispatch(SecondDispatchState.INITIALIZE)

    private fun requestBack() = this.onDispatch(SecondDispatchState.BACK)

    private fun secondInitialize() {
        this.onDispatch(SecondDispatchState.USER_ACTION_WAITING)
        this.onChangeScreen(SecondFragmentScreenState.INITIALIZED)
    }

    private fun back() {
        this.onDispatch(SecondDispatchState.USER_ACTION_WAITING)
        this.onChangeScreen(MainTransitionState.FIRST)
    }

    // endregion

}