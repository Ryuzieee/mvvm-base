package com.example.ryuji_mvvm_architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T1 : BaseViewModel, T2 : ViewDataBinding> : Fragment() {

    // region メンバ変数(初期化必須)

    abstract val receiverMap: Map<ReceiverType, (Any?) -> Unit>

    @LayoutRes
    abstract fun layoutResource(): Int

    abstract fun bindViewModel(viewModel: T1)

    // endregion

    // region メンバ変数

    lateinit var viewModel: T1

    internal lateinit var binding: T2

    // endregion

    // region ライフサイクル

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

    // region メンバ関数(初期化必須)

    private fun getVM(): T1 = (requireActivity() as BaseActivity<*, *>).viewModel as T1

    // endregion

    // region メンバ関数

    private fun binding(inflater: LayoutInflater, container: ViewGroup) {
        binding = DataBindingUtil.inflate(inflater, layoutResource(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        bindViewModel(viewModel)
    }

    internal fun onReceive(receiverType: ReceiverType, parameter: Any? = null) =
        receiverMap[receiverType]?.let { it(parameter) }

    // 初期化したい処理
    open fun initialize() {}

    // endregion

}