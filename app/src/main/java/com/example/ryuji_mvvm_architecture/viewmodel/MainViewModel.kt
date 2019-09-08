package com.example.ryuji_mvvm_architecture.viewmodel

import android.animation.ObjectAnimator
import android.app.Application
import android.view.animation.DecelerateInterpolator
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import com.example.ryuji_mvvm_architecture.state.*

class MainViewModel(application: Application) : BaseViewModel(application) {

    // region LiveData

    var mainTransitionState: MutableLiveData<MainTransitionState> = MutableLiveData()

    var progressState: MutableLiveData<MainTransitionState> = MutableLiveData()

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

    // TODO: dispatchにまとめたい
    fun transition(state: MainTransitionState) {
        progressState.value = state
    }

    // region First Dispatch

    private fun firstDispatch(state: FirstScreenState, dispatchData: Any?) {
        when (state) {
            FirstScreenState.INITIALIZE -> {
                when (firstState.value?.screenState) {
                    FirstScreenState.INITIALIZED, FirstScreenState.FETCHED -> {}
                    else -> {
                        firstState.value = FirstState(
                            screenState = FirstScreenState.INITIALIZED,
                            data = FirstData(
                                text = "INITIALIZE"
                            )
                        )
                    }
                }
            }
            FirstScreenState.FETCH -> {
                firstState.value = FirstState(
                    screenState = FirstScreenState.FETCHED,
                    // TODO: 強制アンラップしちゃってるから考える
                    data = firstState.value!!.data.copy(
                        text = "FETCH"
                    )
                )
            }
            FirstScreenState.NEXT -> {
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
                mainTransitionState.value = MainTransitionState.THIRD
            }
        }
    }

    // endregion

    // region Third Dispatch

    private fun thirdDispatch(state: ThirdScreenState, dispatchData: Any?) {
        when (state) {
            ThirdScreenState.FINISH -> {
                mainTransitionState.value = MainTransitionState.SECOND
            }
        }
    }

    // endregion
}