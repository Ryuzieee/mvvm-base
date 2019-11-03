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

    abstract val propertyId: String

    abstract val receiverMap: Map<FragmentScreenState, (Data) -> Unit>

    // endregion

    // region メンバ変数

    internal lateinit var viewModel: T1

    internal lateinit var binding: T2

    // endregion

    // region メンバ関数(初期化必須)

    abstract fun bindViewModel(viewModel: T1)

    // endregion

    // region ライフサイクル

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutResource, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel = (requireActivity() as BaseActivity<*, *>).viewModel as T1
        bindViewModel(viewModel)
        viewModel.propertyMap[propertyId]?.observe(this, Observer<Property> { property ->
            receiverMap[property.screenState]?.let { it(property.data) }
        })
        initialize()
        return binding.root
    }

    // endregion

    // region メンバ関数

    open fun initialize() {}

    // endregion

}