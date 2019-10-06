package com.example.ryuji_mvvm_architecture.main.fragment

import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.ReceivedType
import com.example.ryuji_mvvm_architecture.databinding.FragmentThirdBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.ThirdScreenState.BACK
import com.example.ryuji_mvvm_architecture.main.ThirdScreenState.INITIAL
import com.example.ryuji_mvvm_architecture.main.fragment.ThirdFragment.ThirdReceivedType.CLICK_BACK_BUTTON

class ThirdFragment : BaseFragment<MainViewModel, FragmentThirdBinding>(MainViewModel::class.java) {

    enum class ThirdReceivedType : ReceivedType {
        CLICK_BACK_BUTTON
    }

    override val onReceivedMap: Map<ReceivedType, (Any?) -> Unit> = mapOf(
        CLICK_BACK_BUTTON to { _ -> viewModel.dispatch(BACK) }
    )

    override fun layoutResource() = R.layout.fragment_third

    override fun bindViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.signupButton.setOnClickListener { onReceived(CLICK_BACK_BUTTON) }
        viewModel.dispatch(INITIAL)
    }
}