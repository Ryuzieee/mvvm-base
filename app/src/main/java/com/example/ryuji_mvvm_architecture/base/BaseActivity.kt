package com.example.ryuji_mvvm_architecture.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.util.FragmentTransitionAnimation

abstract class BaseActivity<T1 : BaseViewModel, T2 : ViewDataBinding>(private val VMClass: Class<T1>) :
    AppCompatActivity() {

    // region メンバ変数(初期化必須)

    abstract val receiverMap: Map<ReceiverType, (Any?) -> Unit>

    abstract val viewModelProviderFactory: ViewModelProvider.Factory

    @LayoutRes
    abstract fun layoutResource(): Int

    abstract fun firstFragment(): Fragment

    abstract fun bindViewModel(viewModel: T1)

    abstract fun animation(): FragmentTransitionAnimation?

    // endregion

    // region メンバ変数

    internal val binding by lazy {
        DataBindingUtil.setContentView(this, layoutResource()) as T2
    }

    internal val viewModel by lazy {
        viewModelProviderFactory.create(VMClass)
    }

    // endregion

    // region ライフサイクル

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel(viewModel)
        initialize()
        if (savedInstanceState == null) createOrReplaceFragment(firstFragment())
    }

    override fun onBackPressed() {
        viewModel.previousTransitionState()?.let { viewModel.dispatch(it) } ?: onBack()
    }

    // endregion

    // region フラグメント管理

    internal fun transition(transitionState: TransitionState) {
        if (viewModel.isBack(supportFragmentManager)) {
            back()
            return
        }
        createOrReplaceFragment(transitionState.fragment)
    }

    private fun createOrReplaceFragment(fragment: Fragment) {
        val isFirstFragment = fragment == firstFragment()
        supportFragmentManager.beginTransaction().run {
            // 初期表示かつアニメーション不使用の場合はAnimationを無効にする
            val animation = animation()
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

    private fun back() = supportFragmentManager.popBackStack()

    // endregion

    // region メンバ関数

    internal fun onReceive(receiverType: ReceiverType, parameter: Any? = null) =
        receiverMap[receiverType]?.let { it(parameter) }

    // 初期化したい処理
    open fun initialize() {}

    private fun onBack() {
        if (supportFragmentManager.backStackEntryCount < 1) {
            super.onBackPressed()
        } else {
            back()
        }
    }

    // endregion

}