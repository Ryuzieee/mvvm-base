package com.example.ryuji_mvvm_architecture.viewmodel

import android.app.Application
import android.os.Handler
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.ryuji_mvvm_architecture.state.*

class MainViewModel(application: Application) : BaseViewModel(application) {

    // region LiveData

    private var parentScreenState = MutableLiveData<ParentScreenState>()

    private var firstState = MutableLiveData<FirstState>()

    private var secondState = MutableLiveData<SecondState>()

    private var thirdState = MutableLiveData<ThirdState>()

    fun getParentScreenState() = parentScreenState

    fun getFirstState() = firstState

    fun getSecondState() = secondState

    fun getThirdState() = thirdState

    fun nextParentScreenState(): ParentScreenState? {
        val current = ParentScreenState.values().indexOf(parentScreenState.value)
        // 現在のindexがMAX未満である場合、現在の表示を最終ページ以外であると見なし、indexを+1したページを返却する
        return if (current < ParentScreenState.values().lastIndex) {
            ParentScreenState.values()[current + 1]
        } else {
            null
        }
    }

    fun previousParentScreenState(): ParentScreenState? {
        val current = ParentScreenState.values().indexOf(parentScreenState.value)
        // 現在のindexが1以上である場合、現在の表示を2ページ目以降と見なし、indexを-1したページを返却する
        return if (current > 0) {
            ParentScreenState.values()[current - 1]
        } else {
            null
        }
    }

    fun isBack(supportFragmentManager: FragmentManager): Boolean {
        // parentTransitionStateのindexが現在のStateよりも小さい場合は画面を戻ると判断しback
        val new = ParentScreenState.values().indexOf(parentScreenState.value)
        val old = ParentScreenState.values().indexOfFirst {
            it.fragment == supportFragmentManager.fragments.first()
        }
        return old > new
    }

    // endregion

    // region Dispatch

    override fun dispatch(state: ScreenState, dispatchData: Any?) {
        when (state) {
            is FirstScreenState -> firstDispatch(state, dispatchData)
            is SecondScreenState -> secondDispatch(state, dispatchData)
            is ThirdScreenState -> thirdDispatch(state, dispatchData)
            is ParentScreenState -> parentDispatch(state)
        }
    }

    // endregion

    // region First Dispatch

    private fun firstDispatch(state: FirstScreenState, dispatchData: Any?) {
        when (state) {
            FirstScreenState.INITIALIZE -> {
                // onCreateで呼ばれるINITIALIZEはstateがnull(初期化されていない)の時のみ実行する
                if (firstState.value == null) {
                    firstState.value = FirstState(
                        screenState = FirstScreenState.INITIALIZED,
                        data = FirstData(
                            text = "INITIALIZE"
                        )
                    )
                }
            }
            FirstScreenState.FETCH -> {
                firstState.value = firstState.value?.copy(
                    screenState = FirstScreenState.FETCHED,
                    data = firstState.value?.data?.copy(
                        text = "FETCH"
                    ) ?: FirstData()
                )
            }
            FirstScreenState.NEXT -> nextParentScreenState()?.let { parentDispatch(it) }
            else -> {
            }
        }
    }

    // endregion

    // region Second Dispatch

    private fun secondDispatch(state: SecondScreenState, dispatchData: Any?) {
        when (state) {
            SecondScreenState.FETCH_FROM_SERVER -> {
                // onCreateで呼ばれるFETCH_FROM_SERVERはstateがnull(初期化されていない)の時のみ実行する
                if (secondState.value == null) {
                    secondState.value = SecondState(
                        screenState = SecondScreenState.LOADING,
                        data = SecondData()
                    )
                    Handler().postDelayed({
                        // API実行(5秒で取得)
                        secondState.value = secondState.value?.copy(
                            screenState = SecondScreenState.FETCHED,
                            data = secondState.value?.data?.copy(
                                text = "NEXT"
                            ) ?: SecondData()
                        )
                    }, 3000)
                }
            }
            SecondScreenState.LOADING -> {
                // Nothing
            }
            SecondScreenState.FETCHED -> {
                // Nothing
            }
            SecondScreenState.NEXT -> nextParentScreenState()?.let { parentDispatch(it) }
        }
    }

    // endregion

    // region Third Dispatch

    private fun thirdDispatch(state: ThirdScreenState, dispatchData: Any?) {
        when (state) {
            ThirdScreenState.BACK -> previousParentScreenState()?.let { parentDispatch(it) }
        }
    }

    // endregion

    // region Parent Dispatch

    private fun parentDispatch(state: ParentScreenState) {
        parentScreenState.value = state
    }

    // endregion

    // region validation test

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val termOfUse = MutableLiveData<Boolean>()

    private fun isValid() =
        !username.value.isNullOrBlank() && !password.value.isNullOrBlank() && termOfUse.value == true

    val canSubmit = MediatorLiveData<Boolean>().also { result ->
        result.addSource(username) { result.value = isValid() }
        result.addSource(password) { result.value = isValid() }
        result.addSource(termOfUse) { result.value = isValid() }
    }

    // endregion

}