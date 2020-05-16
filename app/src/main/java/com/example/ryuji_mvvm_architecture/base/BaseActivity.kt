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

abstract class BaseActivity<T1 : BaseViewModel, T2 : ViewDataBinding>(private val viewModelClass: Class<T1>) :
    AppCompatActivity() {

    // region メンバ変数(初期化必須)

    abstract val layoutResource: Int

    abstract val firstFragment: Fragment

    abstract val transitionAnimation: FragmentTransitionAnimation?

    abstract val viewModelProviderFactory: ViewModelProvider.Factory

    abstract val observerMap: Map<TransitionState, (TransitionState) -> Unit>

    // endregion

    // region メンバ変数

    internal val viewModel by lazy { viewModelProviderFactory.create(viewModelClass) }

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
            observerMap[transitionState]?.let { it(transitionState) }
        })
        initialize()
        if (savedInstanceState == null) createOrReplaceFragment(firstFragment)
    }

    // endregion

    // region フラグメント管理

    protected fun createOrReplaceFragment(fragment: Fragment) {
        val isFirstFragment = fragment == firstFragment
        supportFragmentManager.beginTransaction().run {
            // 初期表示かつアニメーション不使用の場合はAnimationを無効にする
            val animation = transitionAnimation
            if (!isFirstFragment && animation != null) {
                setCustomAnimations(
                    animation.enter,
                    animation.exit,
                    animation.popEnter,
                    animation.popExit
                )
            }
            replace(R.id.container, fragment)
            // 初期表示の場合はBackStackを無効にする
            if (!isFirstFragment) addToBackStack(null)
            commit()
        }
    }

    protected fun removeFragment() = supportFragmentManager.popBackStack()

    // endregion

    // region メンバ関数

    open fun initialize() {}

    // endregion

}