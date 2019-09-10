package com.example.ryuji_mvvm_architecture.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.ryuji_mvvm_architecture.state.*

class MainViewModel(application: Application) : BaseViewModel(application) {

    // region LiveData

    var parentScreenState: MutableLiveData<ParentScreenState> = MutableLiveData()

    var progressState: MutableLiveData<ParentScreenState> = MutableLiveData()

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
            is ParentScreenState -> parentDispatch(state, dispatchData)
        }
    }

    // endregion

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
                    data = firstState.value?.data?.copy(
                        text = "FETCH"
                    ) ?: FirstData(text = "INITIALIZE")
                )
            }
            FirstScreenState.NEXT -> {
                parentScreenState.value = ParentScreenState.SECOND
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
                parentScreenState.value = ParentScreenState.THIRD
            }
        }
    }

    // endregion

    // region Third Dispatch

    private fun thirdDispatch(state: ThirdScreenState, dispatchData: Any?) {
        when (state) {
            ThirdScreenState.FINISH -> {
                parentScreenState.value = ParentScreenState.SECOND
            }
        }
    }

    // endregion

    // region Parent Dispatch

    private fun parentDispatch(state: ParentScreenState, dispatchData: Any?) {
        progressState.value = state
    }

    // endregion
}