package com.example.ryuji_mvvm_architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding>(private val VMClass: Class<VM>) : Fragment() {

    // region Property

    lateinit var viewModel: VM

    open lateinit var binding: DB

    // endregion

    // region Life Cycle

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

    // region Private Function

    private fun binding(inflater: LayoutInflater, container: ViewGroup) {
        binding = DataBindingUtil.inflate(inflater, layoutResource(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initializeViewModel(viewModel)
    }

    abstract fun initializeViewModel(viewModel: VM)

    // TODO: ViewModelFactory使ってもいいかも
    private fun getVM(): VM = ViewModelProvider(activity!!).get(VMClass)

    // endregion

    // region Must Implement For Initialize

    @LayoutRes
    abstract fun layoutResource(): Int

    // endregion

    // region Option

    // 初期化したい処理があれば使用する
    open fun initialize() {}

    // endregion
}