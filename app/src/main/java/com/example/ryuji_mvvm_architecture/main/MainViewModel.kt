package com.example.ryuji_mvvm_architecture.main

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.example.ryuji_mvvm_architecture.base.BaseViewModel
import com.example.ryuji_mvvm_architecture.base.Property
import com.example.ryuji_mvvm_architecture.base.ScreenState
import com.example.ryuji_mvvm_architecture.base.TransitionState

class MainViewModel(private val provider: MainProvider) : BaseViewModel() {

    // region LiveData

    override val transitionState: MutableLiveData<TransitionState> = MutableLiveData(MainTransitionState.FIRST)

    override val transitionStateList = MainTransitionState.values().asList()

    override val propertyMap: Map<String, MutableLiveData<out Property>> = mapOf(
        FirstScreenState.values().first().id() to MutableLiveData<FirstProperty>(
            FirstProperty(
                FirstScreenState.INITIAL,
                FirstData()
            )
        ),
        SecondScreenState.values().first().id() to MutableLiveData<SecondProperty>(
            SecondProperty(
                SecondScreenState.INITIAL,
                SecondData()
            )
        ),
        ThirdScreenState.values().first().id() to MutableLiveData<ThirdProperty>(
            ThirdProperty(
                ThirdScreenState.INITIAL,
                ThirdData()
            )
        )
    )

    val mainTransitionState = transitionState as MutableLiveData<MainTransitionState>
    val firstProperty = propertyMap[FirstScreenState.values().first().id()] as MutableLiveData<FirstProperty>
    val secondProperty = propertyMap[SecondScreenState.values().first().id()] as MutableLiveData<SecondProperty>
    val thirdProperty = propertyMap[ThirdScreenState.values().first().id()] as MutableLiveData<ThirdProperty>

    // endregion

    // region Dispatch

    override val functionMap: Map<ScreenState, (() -> Unit)?> = mapOf(
        FirstScreenState.INITIAL to null,
        FirstScreenState.NEXT to { firstScreenStateNext() },
        SecondScreenState.INITIAL to { secondScreenStateFetchFromServer() },
        SecondScreenState.LOADING to null,
        SecondScreenState.FETCHED to null,
        SecondScreenState.NEXT to { secondScreenStateNext() },
        ThirdScreenState.INITIAL to { thirdScreenStateInitial() },
        ThirdScreenState.START_NEXT_ACTIVITY to null
    )

    private fun firstScreenStateNext() {
        nextTransitionState()?.let { dispatch(it) }
    }

    private fun secondScreenStateFetchFromServer() {
        Handler().postDelayed({
            dispatch(SecondScreenState.FETCHED, secondProperty.value?.data?.copy(text = provider.request()))
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

    // endregion

}