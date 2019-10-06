package com.example.ryuji_mvvm_architecture.main.fragment

import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.databinding.FragmentThirdBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.ThirdProperty
import com.example.ryuji_mvvm_architecture.main.ThirdScreenState
import com.example.ryuji_mvvm_architecture.base.ReceivedType

class ThirdFragment :
    BaseFragment<MainViewModel, FragmentThirdBinding>(MainViewModel::class.java) {

    enum class ThirdReceivedType : ReceivedType {
        CLICK_BACK_BUTTON
    }

    override val onReceivedMap: Map<ReceivedType, (Any?) -> Unit> = mapOf(
        ThirdReceivedType.CLICK_BACK_BUTTON to { _ -> viewModel.dispatch(ThirdScreenState.BACK) }
    )

    override fun layoutResource() = R.layout.fragment_third

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.signupButton.setOnClickListener { onReceived(ThirdReceivedType.CLICK_BACK_BUTTON) }
        viewModel.dispatch(ThirdScreenState.INITIAL)
    }
}