package com.example.ryuji_mvvm_architecture.view.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.viewmodel.BaseViewModel

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>(private val VMClass: Class<VM>) :
    AppCompatActivity() {

    // region Property

    val binding by lazy {
        DataBindingUtil.setContentView(this, layoutResource()) as DB
    }

    val viewModel by lazy {
        ViewModelProviders.of(this).get(VMClass)
    }

    // endregion

    // region Life Cycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel(viewModel)
        initialize()
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, firstFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    // endregion

    // region Must Implement For Initialize

    @LayoutRes
    abstract fun layoutResource(): Int

    abstract fun firstFragment(): Fragment

    abstract fun initializeViewModel(viewModel: VM)

    // endregion

    // region Fragment Control

    // TODO: ここの在り方は考える

    open fun next(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    open fun prev() = supportFragmentManager.popBackStack()

    // endregion

    // region Option

    // 初期化したい処理があれば使用する
    open fun initialize() {}

    // endregion
}