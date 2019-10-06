package com.example.ryuji_mvvm_architecture.main.fragment

import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.ReceivedType
import com.example.ryuji_mvvm_architecture.databinding.FragmentFirstBinding
import com.example.ryuji_mvvm_architecture.main.FirstScreenState.INITIAL
import com.example.ryuji_mvvm_architecture.main.FirstScreenState.NEXT
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.fragment.FirstFragment.FirstReceivedType.CLICK_NEXT_BUTTON

class FirstFragment : BaseFragment<MainViewModel, FragmentFirstBinding>(MainViewModel::class.java) {

    enum class FirstReceivedType : ReceivedType {
        CLICK_NEXT_BUTTON
    }

    override val onReceivedMap: Map<ReceivedType, (Any?) -> Unit> = mapOf(
        CLICK_NEXT_BUTTON to { _ -> viewModel.dispatch(NEXT) }
    )

    override fun layoutResource() = R.layout.fragment_first

    override fun bindViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { onReceived(CLICK_NEXT_BUTTON) }
        viewModel.dispatch(INITIAL)
    }
}