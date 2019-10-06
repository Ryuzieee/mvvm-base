package com.example.ryuji_mvvm_architecture.main.fragment

import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.databinding.FragmentThirdBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.ThirdProperty
import com.example.ryuji_mvvm_architecture.main.ThirdScreenState

class ThirdFragment :
    BaseFragment<MainViewModel, FragmentThirdBinding, ThirdFragment.ThirdReceivedType>(MainViewModel::class.java) {

    enum class ThirdReceivedType {
        CLICK_BACK_BUTTON
    }

    override val onReceivedMap: Map<ThirdReceivedType, (Any?) -> Unit> = mapOf(
        ThirdReceivedType.CLICK_BACK_BUTTON to { _ -> viewModel.dispatch(ThirdScreenState.BACK) }
    )

    override fun layoutResource() = R.layout.fragment_third

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.signupButton.setOnClickListener { onReceived(ThirdReceivedType.CLICK_BACK_BUTTON) }
        viewModel.thirdProperty.observe(viewLifecycleOwner, Observer<ThirdProperty> {})
        viewModel.dispatch(ThirdScreenState.INITIAL)
    }
}