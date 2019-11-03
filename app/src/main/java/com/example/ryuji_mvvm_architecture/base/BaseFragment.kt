package com.example.ryuji_mvvm_architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class BaseFragment<T1 : BaseViewModel, T2 : ViewDataBinding> : Fragment() {

    // region メンバ変数(初期化必須)

    abstract val layoutResource: Int

    abstract val receiverMap: Map<FragmentScreenState, (Data) -> Unit>

    abstract val propertyId: String

    // endregion

    // region メンバ変数

    internal lateinit var viewModel: T1

    internal lateinit var binding: T2

    // endregion

    // region ライフサイクル

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (requireActivity() as BaseActivity<*, *>).viewModel as T1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutResource, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        bindViewModel(viewModel)
        viewModel.propertyMap[propertyId]?.observe(this, Observer<Property> {
            onReceive(it.fragmentScreenState, it.data)
        })
        initialize()
        return binding.root
    }

    // endregion

    // region メンバ関数(初期化必須)

    abstract fun bindViewModel(viewModel: T1)

    // endregion

    // region メンバ関数

    private fun onReceive(fragmentScreenState: FragmentScreenState, data: Data) =
        receiverMap[fragmentScreenState]?.let { it(data) }

    open fun initialize() {}

    // endregion

}