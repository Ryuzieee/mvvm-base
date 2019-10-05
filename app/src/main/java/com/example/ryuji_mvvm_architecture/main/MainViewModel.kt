package com.example.ryuji_mvvm_architecture.main

import android.app.Application
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.example.ryuji_mvvm_architecture.base.BaseViewModel
import com.example.ryuji_mvvm_architecture.base.Property
import com.example.ryuji_mvvm_architecture.base.ScreenState
import com.example.ryuji_mvvm_architecture.base.TransitionState

class MainViewModel(application: Application) : BaseViewModel(application) {

    // region LiveData

    override val transitionState = MutableLiveData<TransitionState>()

    override val transitionStateList = ParentScreenState.values().asList()

    override val propertyList: List<MutableLiveData<out Property>> = listOf(
        MutableLiveData<FirstProperty>(FirstProperty(screenState = FirstScreenState.INITIALIZE, data = FirstData())),
        MutableLiveData<SecondProperty>(
            SecondProperty(
                screenState = SecondScreenState.FETCH_FROM_SERVER,
                data = SecondData()
            )
        ),
        MutableLiveData<ThirdProperty>(ThirdProperty(screenState = ThirdScreenState.INITIAL, data = ThirdData()))
    )

    val firstProperty = propertyList[0] as MutableLiveData<FirstProperty>
    val secondProperty = propertyList[1] as MutableLiveData<SecondProperty>
    val thirdProperty = propertyList[2] as MutableLiveData<ThirdProperty>

    // endregion

    // region Dispatch

    override val functionMap: Map<ScreenState, (() -> Unit)?> = mapOf(
        FirstScreenState.INITIALIZE to null,
        FirstScreenState.NEXT to { firstScreenStateNext() },
        SecondScreenState.FETCH_FROM_SERVER to { secondScreenStateFetchFromServer() },
        SecondScreenState.LOADING to {},
        SecondScreenState.FETCHED to null,
        SecondScreenState.NEXT to { secondScreenStateNext() },
        ThirdScreenState.INITIAL to { thirdScreenStateInitial() },
        ThirdScreenState.BACK to { thirdScreenStateBack() }
    )

    private fun firstScreenStateNext() {
        nextTransitionState()?.let { dispatch(it) }
    }

    private fun secondScreenStateFetchFromServer() {
        Handler().postDelayed({
            dispatch(SecondScreenState.FETCHED, secondProperty.value?.data?.copy(text = "FETCHED"))
        }, 3000)
    }

    private fun secondScreenStateNext() {
        nextTransitionState()?.let { dispatch(it) }
    }

    private fun thirdScreenStateInitial() {
        thirdProperty.value?.data?.let { data ->
            // Observerのリストを作成
            val observerList = listOf(data.username, data.password, data.termOfUse)
            // canSubmit(MediatorLiveData)からObserverのリストを削除
            observerList.forEach { data.canSubmit.removeSource(it) }
            // canSubmit(MediatorLiveData)にObserverのリストを追加
            observerList.forEach { data.canSubmit.addSource(it) { data.canSubmit.value = data.isValid() } }
        }
    }

    private fun thirdScreenStateBack() {
        previousTransitionState()?.let { dispatch(it) }
    }

    // endregion

}