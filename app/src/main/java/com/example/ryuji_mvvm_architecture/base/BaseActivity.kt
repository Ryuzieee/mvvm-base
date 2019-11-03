package com.example.ryuji_mvvm_architecture.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.util.FragmentTransitionAnimation

abstract class BaseActivity<T1 : BaseViewModel, T2 : ViewDataBinding>(private val VMClass: Class<T1>) :
    AppCompatActivity() {

    // region メンバ変数(初期化必須)

    abstract val layoutResource: Int

    abstract val firstFragment: Fragment

    abstract val transitionAnimation: FragmentTransitionAnimation?

    abstract val viewModelProviderFactory: ViewModelProvider.Factory

    abstract val receiverMap: Map<TransitionState, (TransitionState) -> Unit>

    // endregion

    // region メンバ変数

    internal val viewModel by lazy { viewModelProviderFactory.create(VMClass) }

    internal val binding by lazy { DataBindingUtil.setContentView(this, layoutResource) as T2 }

    // endregion

    // region メンバ関数(初期化必須)

    abstract fun bindViewModel(viewModel: T1)

    // endregion

    // region ライフサイクル

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel(viewModel)
        viewModel.transitionState.observe(this, Observer<TransitionState> { transitionState ->
            goBackOrForward(transitionState)
            receiverMap[transitionState]?.let { it(transitionState) }
        })
        initialize()
        if (savedInstanceState == null) createOrReplaceFragment(firstFragment)
    }

    override fun onBackPressed() {
        viewModel.previousTransitionState()?.let { viewModel.dispatch(it) } ?: super.onBackPressed()
    }

    // endregion

    // region フラグメント管理

    private fun goBackOrForward(transitionState: TransitionState) {
        if (viewModel.isBack(supportFragmentManager)) {
            supportFragmentManager.popBackStack()
            return
        }
        createOrReplaceFragment(transitionState.fragment)
    }

    private fun createOrReplaceFragment(fragment: Fragment) {
        val isFirstFragment = fragment == firstFragment
        supportFragmentManager.beginTransaction().run {
            // 初期表示かつアニメーション不使用の場合はAnimationを無効にする
            val animation = transitionAnimation
            if (!isFirstFragment && animation != null) {
                setCustomAnimations(
                    animation.enter ?: 0,
                    animation.exit ?: 0,
                    animation.popEnter ?: 0,
                    animation.popExit ?: 0
                )
            }
            replace(R.id.container, fragment)
            // 初期表示の場合はBackStackを無効にする
            if (!isFirstFragment) addToBackStack(null)
            commit()
        }
    }

    // endregion

    // region メンバ関数

    open fun initialize() {}

    // endregion

}