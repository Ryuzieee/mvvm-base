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
import com.example.ryuji_mvvm_architecture.util.ReceivedType

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>(private val VMClass: Class<VM>) :
    AppCompatActivity() {

    // region Property

    val binding by lazy {
        DataBindingUtil.setContentView(this, layoutResource()) as DB
    }

    val viewModel by lazy {
        // TODO: ViewModelFactory使ってもいいかも
        ViewModelProvider(this).get(VMClass)
    }

    // endregion

    // region Life Cycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel(viewModel)
        initialize()
        if (savedInstanceState == null) createOrReplaceFragment(firstFragment())
    }

    override fun onBackPressed() {
        viewModel.previousTransitionState()?.let { viewModel.dispatch(it) } ?: onBack()
    }

    private fun onBack() {
        if (supportFragmentManager.backStackEntryCount < 1) {
            super.onBackPressed()
        } else {
            back()
        }
    }

    // endregion

    // region Must Implement For Initialize

    abstract val onReceivedMap: Map<ReceivedType, (() -> Unit)>

    @LayoutRes
    abstract fun layoutResource(): Int

    abstract fun firstFragment(): Fragment

    abstract fun initializeViewModel(viewModel: VM)

    abstract fun animation(): FragmentTransitionAnimation?

    // endregion

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

    // 初期化したい処理があれば使用する
    open fun initialize() {}

    open fun onReceived(onReceived: ReceivedType) = onReceivedMap[onReceived]?.let { it() }

    // endregion

}