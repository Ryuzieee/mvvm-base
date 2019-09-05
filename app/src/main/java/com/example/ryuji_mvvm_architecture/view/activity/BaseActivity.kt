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

    // region property

    val binding by lazy {
        DataBindingUtil.setContentView(this, layoutResource()) as DB
    }

    val viewModel by lazy {
        ViewModelProviders.of(this).get(VMClass)
    }

    // endregion

    // region life cycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel(viewModel)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, firstFragment())
                .commit()
        }
        initialize()
    }

    // endregion

    // region must implement for initialize

    @LayoutRes
    abstract fun layoutResource(): Int

    abstract fun firstFragment(): Fragment

    abstract fun initializeViewModel(viewModel: VM)

    // endregion

    // region option

    // 初期化したい処理があれば使用する
    open fun initialize() {}

    // endregion
}