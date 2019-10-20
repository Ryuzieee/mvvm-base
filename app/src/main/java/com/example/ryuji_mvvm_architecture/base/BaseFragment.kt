package com.example.ryuji_mvvm_architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    // region Property

    lateinit var viewModel: BaseViewModel

    open lateinit var binding: T

    // endregion

    // region Must Implement For Initialize

    abstract val receiverMap: Map<ReceiverType, (Any?) -> Unit>


    @LayoutRes
    abstract fun layoutResource(): Int

    abstract fun bindViewModel(viewModel: BaseViewModel)

    // 初期化したい処理
    abstract fun initialize()

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
        bindViewModel(viewModel)
    }

    private fun getVM(): BaseViewModel = (requireActivity() as BaseActivity<*, *>).viewModel

    // endregion

    // region Option

    open fun onReceive(receiverType: ReceiverType, parameter: Any? = null) =
        receiverMap[receiverType]?.let { it(parameter) }

    // endregion

}