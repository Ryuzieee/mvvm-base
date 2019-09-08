package com.example.ryuji_mvvm_architecture.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.ryuji_mvvm_architecture.state.*

class MainViewModel(application: Application) : BaseViewModel(application) {

    // region LiveData

    var mainTransitionState: MutableLiveData<MainTransitionState> = MutableLiveData()

    var firstState: MutableLiveData<FirstState> = MutableLiveData()

    var secondState: MutableLiveData<SecondState> = MutableLiveData()

    var thirdState: MutableLiveData<ThirdState> = MutableLiveData()

    // endregion

    // region Dispatch

    override fun dispatch(state: ScreenState, dispatchData: Any?) {
        when (state) {
            is FirstScreenState -> firstDispatch(state, dispatchData)
            is SecondScreenState -> secondDispatch(state, dispatchData)
            is ThirdScreenState -> thirdDispatch(state, dispatchData)
        }
    }

    // endregion

    // region First Dispatch

    private fun firstDispatch(state: FirstScreenState, dispatchData: Any?) {
        when (state) {
            FirstScreenState.INITIALIZE -> {
                firstState.value = FirstState(
                    screenState = FirstScreenState.INITIALIZE,
                    data = FirstData(
                        text = "INITIALIZE"
                    )
                )
            }
            FirstScreenState.FETCH -> {
                firstState.value = FirstState(
                    screenState = FirstScreenState.FETCH,
                    // TODO: 強制アンラップしちゃってるから考える
                    data = firstState.value!!.data.copy(
                        text = "FETCH"
                    )
                )
            }
            FirstScreenState.NEXT -> {
                firstState.value = firstState.value?.copy(
                    screenState = FirstScreenState.NEXT
                )
                // TODO: 画面遷移の場合はいいタイミングで各Stateを初期化する
                mainTransitionState.value = MainTransitionState.SECOND
            }
            else -> {
            }
        }
    }

    // endregion

    // region Second Dispatch

    private fun secondDispatch(state: SecondScreenState, dispatchData: Any?) {
        when (state) {
            SecondScreenState.NEXT -> {
                secondState.value = secondState.value?.copy(
                    screenState = SecondScreenState.NEXT
                )
                // TODO: 画面遷移の場合はいいタイミングで各Stateを初期化する
                mainTransitionState.value = MainTransitionState.THIRD
            }
            else -> {
            }
        }
    }

    // endregion

    // region Third Dispatch

    private fun thirdDispatch(state: ThirdScreenState, dispatchData: Any?) {
        when (state) {
            ThirdScreenState.FINISH -> {
                thirdState.value = thirdState.value?.copy(
                    screenState = ThirdScreenState.FINISH
                )
                // TODO: 画面遷移の場合はいいタイミングで各Stateを初期化する
                mainTransitionState.value = MainTransitionState.BACK
            }
            else -> {
            }
        }
    }

    // endregion
}