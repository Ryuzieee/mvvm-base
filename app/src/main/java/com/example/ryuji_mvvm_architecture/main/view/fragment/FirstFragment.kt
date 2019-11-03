package com.example.ryuji_mvvm_architecture.main.view.fragment

import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.ReceiverType
import com.example.ryuji_mvvm_architecture.databinding.FragmentFirstBinding
import com.example.ryuji_mvvm_architecture.main.FirstScreenState
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.view.fragment.FirstFragment.FirstReceiverType.CLICK_NEXT_BUTTON

class FirstFragment : BaseFragment<MainViewModel, FragmentFirstBinding>() {

    enum class FirstReceiverType : ReceiverType {
        CLICK_NEXT_BUTTON
    }

    override val receiverMap: Map<ReceiverType, (Any?) -> Unit> = mapOf(
        CLICK_NEXT_BUTTON to { _ -> viewModel.dispatch(FirstScreenState.NEXT) }
    )

    override fun layoutResource() = com.example.ryuji_mvvm_architecture.R.layout.fragment_first

    override fun bindViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { onReceive(CLICK_NEXT_BUTTON) }
        viewModel.dispatch(FirstScreenState.INITIAL)
    }
}