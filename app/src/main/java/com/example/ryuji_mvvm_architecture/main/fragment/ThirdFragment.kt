package com.example.ryuji_mvvm_architecture.main.fragment

import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.BaseViewModel
import com.example.ryuji_mvvm_architecture.base.ReceiverType
import com.example.ryuji_mvvm_architecture.databinding.FragmentThirdBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.ThirdScreenState.BACK
import com.example.ryuji_mvvm_architecture.main.ThirdScreenState.INITIAL
import com.example.ryuji_mvvm_architecture.main.fragment.ThirdFragment.ThirdReceiverType.CLICK_BACK_BUTTON

class ThirdFragment : BaseFragment<FragmentThirdBinding>() {

    enum class ThirdReceiverType : ReceiverType {
        CLICK_BACK_BUTTON
    }

    override val receiverMap: Map<ReceiverType, (Any?) -> Unit> = mapOf(
        CLICK_BACK_BUTTON to { _ -> viewModel.dispatch(BACK) }
    )

    override fun layoutResource() = R.layout.fragment_third

    override fun bindViewModel(viewModel: BaseViewModel) {
        binding.viewModel = viewModel as MainViewModel
    }

    override fun initialize() {
        binding.signupButton.setOnClickListener { onReceive(CLICK_BACK_BUTTON) }
        viewModel.dispatch(INITIAL)
    }
}