package com.example.ryuji_mvvm_architecture.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.model.MainProvider
import com.example.ryuji_mvvm_architecture.state.*

class MainViewModel(application: Application) : BaseViewModel(application) {

    // region LiveData

    var mainTransitionState: MutableLiveData<MainTransitionState> = MutableLiveData()

    var mainState: MutableLiveData<MainState> = MutableLiveData()

    var secondState: MutableLiveData<SecondState> = MutableLiveData()

    // endregion

    override fun dispatch(state: Any) {
        when(state) {
            is MainScreenState -> {
                when (state) {
                    MainScreenState.INITIALIZE -> initMainText()
                    MainScreenState.FETCH -> fetchMainText()
                    MainScreenState.FINISH -> toSecond()
                }
            }
            is SecondScreenState -> {
                when (state) {
                    SecondScreenState.INITIALIZE -> initSecondText()
                }
            }
        }
    }

    // region Main

    private fun initMainText() {
        mainState.value = MainState(
            screenState = MainScreenState.INITIALIZE,
            data = MainData(
                text = "MAIN"
            )
        )
    }

    private fun fetchMainText() {
        mainState.value = MainState(
            screenState = MainScreenState.FETCH,
            data = MainData(
                text = MainProvider().fetchText()
            )
        )
    }

    private fun toSecond() {
        mainState.value = mainState.value?.copy(
            screenState = MainScreenState.FINISH
        )
        mainTransitionState.value = MainTransitionState.SECOND
    }

    // endregion

    // region Second

    private fun initSecondText() {
        secondState.value = SecondState(
            screenState = SecondScreenState.INITIALIZE,
            data = SecondData(
                text = "SECOND"
            )
        )
    }

    // endregion
}