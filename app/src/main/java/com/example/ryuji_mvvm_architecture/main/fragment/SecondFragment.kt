package com.example.ryuji_mvvm_architecture.main.fragment

import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.BaseViewModel
import com.example.ryuji_mvvm_architecture.base.ReceiverType
import com.example.ryuji_mvvm_architecture.databinding.FragmentSecondBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.SecondScreenState
import com.example.ryuji_mvvm_architecture.main.SecondScreenState.INITIAL
import com.example.ryuji_mvvm_architecture.main.fragment.SecondFragment.SecondReceiverType.CLICK_NEXT_BUTTON

class SecondFragment : BaseFragment<FragmentSecondBinding>() {

    enum class SecondReceiverType : ReceiverType {
        CLICK_NEXT_BUTTON
    }

    override val receiverMap: Map<ReceiverType, (Any?) -> Unit> = mapOf(
        CLICK_NEXT_BUTTON to { _ -> viewModel.dispatch(SecondScreenState.NEXT) }
    )

    override fun layoutResource() = R.layout.fragment_second

    override fun bindViewModel(viewModel: BaseViewModel) {
        binding.viewModel = viewModel as MainViewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { onReceive(CLICK_NEXT_BUTTON) }
        viewModel.dispatch(INITIAL)
    }
}