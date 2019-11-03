package com.example.ryuji_mvvm_architecture.main

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.example.ryuji_mvvm_architecture.base.BaseViewModel
import com.example.ryuji_mvvm_architecture.base.Property
import com.example.ryuji_mvvm_architecture.base.ScreenState
import com.example.ryuji_mvvm_architecture.base.TransitionState
import com.example.ryuji_mvvm_architecture.main.provider.MainProvider

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

    val firstProperty = propertyMap[FirstScreenState.values().first().id()] as MutableLiveData<FirstProperty>
    val secondProperty = propertyMap[SecondScreenState.values().first().id()] as MutableLiveData<SecondProperty>
    val thirdProperty = propertyMap[ThirdScreenState.values().first().id()] as MutableLiveData<ThirdProperty>

    // endregion

    // region Dispatch

    override val businessLogicMap: Map<ScreenState, ((Any?) -> Unit)?> = mapOf(
        FirstScreenState.INITIAL to { _ -> null },
        FirstScreenState.NEXT to { any -> firstScreenStateNext(any) },
        SecondScreenState.INITIAL to { any -> secondScreenStateFetchFromServer(any) },
        SecondScreenState.LOADING to { _ -> null },
        SecondScreenState.FETCHED to { _ -> null },
        SecondScreenState.NEXT to { any -> secondScreenStateNext(any) },
        ThirdScreenState.INITIAL to { any -> thirdScreenStateInitial(any) },
        ThirdScreenState.START_NEXT_ACTIVITY to { _ -> null }
    )

    private fun firstScreenStateNext(any: Any?) {
        nextTransitionState()?.let { dispatch(it) }
    }

    private fun secondScreenStateFetchFromServer(any: Any?) {
        Handler().postDelayed({
            dispatch(SecondScreenState.FETCHED, secondProperty.value?.data?.copy(text = provider.request()))
        }, 3000)
    }

    private fun secondScreenStateNext(any: Any?) {
        nextTransitionState()?.let { dispatch(it) }
    }

    private fun thirdScreenStateInitial(any: Any?) {
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