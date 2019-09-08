package com.example.ryuji_mvvm_architecture.view.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.viewmodel.BaseViewModel

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
        supportActionBar?.title = null
        initializeViewModel(viewModel)
        initialize()
        if (savedInstanceState == null) transition(firstFragment())
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount < 1) {
            super.onBackPressed()
        } else {
            back()
        }
    }

    // endregion

    // region Must Implement For Initialize

    @LayoutRes
    abstract fun layoutResource(): Int

    abstract fun firstFragment(): Fragment

    abstract fun initializeViewModel(viewModel: VM)

    abstract fun transitionAnimation(): Boolean

    // endregion

    // region Fragment Control

    // TODO: ここの在り方は考える

    open fun transition(fragment: Fragment) {

        val isFirstFragment = fragment == firstFragment()
        supportFragmentManager.beginTransaction().run {
            // 初期表示かつアニメーション不使用の場合はAnimationを無効にする
            if (!isFirstFragment && transitionAnimation()) {
                setCustomAnimations(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
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

    // endregion

}