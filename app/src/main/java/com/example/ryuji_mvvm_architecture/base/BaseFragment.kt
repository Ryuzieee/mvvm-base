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

    abstract val observerMap: Map<FragmentScreenState, (ScreenData) -> Unit>

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
        this.binding = DataBindingUtil.inflate(inflater, layoutResource, container, false)
        this.binding.lifecycleOwner = viewLifecycleOwner
        this.viewModel = (requireActivity() as BaseActivity<*, *>).viewModel as T1
        this.bindViewModel(this.viewModel)
        this.viewModel.propertyMap[propertyId]?.observe(this, Observer<Property> { property ->
            this.observerMap[property.fragmentScreenState]?.let { it(property.screenData) }
        })
        this.initialize()
        return this.binding.root
    }

    // endregion

    // region メンバ関数

    open fun initialize() {}

    protected fun onAction(actionState: ActionState, actionData: ActionData? = null) {
        this.viewModel.onAction(actionState, actionData)
    }

    // endregion

}