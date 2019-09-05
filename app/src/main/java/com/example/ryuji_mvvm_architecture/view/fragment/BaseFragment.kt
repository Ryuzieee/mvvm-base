package com.example.ryuji_mvvm_architecture.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.ryuji_mvvm_architecture.viewmodel.BaseViewModel

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding>(private val VMClass: Class<VM>) : Fragment() {

    // region property

    lateinit var viewModel: VM

    open lateinit var binding: DB

    // endregion

    // region life cycle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getVM()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding(inflater, container!!)
        initialize()
        return binding.root
    }

    // endregion

    // region private function

    private fun binding(inflater: LayoutInflater, container: ViewGroup) {
        binding = DataBindingUtil.inflate(inflater, layoutResource(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initializeViewModel(viewModel)
    }

    abstract fun initializeViewModel(viewModel: VM)

    private fun getVM(): VM = ViewModelProviders.of(this).get(VMClass)

    // endregion

    // region must implement for initialize

    @LayoutRes
    abstract fun layoutResource(): Int

    // endregion

    // region option

    // 初期化したい処理があれば使用する
    open fun initialize() {}

    // endregion
}