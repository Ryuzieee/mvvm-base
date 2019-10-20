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

    // region Property

    val binding by lazy {
        DataBindingUtil.setContentView(this, layoutResource()) as T2
    }

    val viewModel by lazy {
        viewModelProviderFactory.create(VMClass)
    }

    // endregion

    // region Must Implement For Initialize

    abstract val receiverMap: Map<ReceiverType, (Any?) -> Unit>

    abstract val viewModelProviderFactory: ViewModelProvider.Factory

    @LayoutRes
    abstract fun layoutResource(): Int

    abstract fun firstFragment(): Fragment

    abstract fun bindViewModel(viewModel: T1)

    abstract fun animation(): FragmentTransitionAnimation?

    // 初期化したい処理
    abstract fun initialize()

    // endregion

    // region Life Cycle

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

    private fun onBack() {
        if (supportFragmentManager.backStackEntryCount < 1) {
            super.onBackPressed()
        } else {
            back()
        }
    }

    // region Fragment Control

    open fun transition(transitionState: TransitionState) {
        if (viewModel.isBack(supportFragmentManager)) {
            back()
            return
        }
        createOrReplaceFragment(transitionState.fragment)
    }

    open fun createOrReplaceFragment(fragment: Fragment) {
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

    open fun back() = supportFragmentManager.popBackStack()

    // endregion

    // region Option

    open fun onReceive(receiverType: ReceiverType, parameter: Any? = null) =
        receiverMap[receiverType]?.let { it(parameter) }

    // endregion

}