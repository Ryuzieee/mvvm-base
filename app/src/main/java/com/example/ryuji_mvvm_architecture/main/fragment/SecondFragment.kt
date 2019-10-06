package com.example.ryuji_mvvm_architecture.main.fragment

import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.ReceivedType
import com.example.ryuji_mvvm_architecture.databinding.FragmentSecondBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.SecondScreenState
import com.example.ryuji_mvvm_architecture.main.SecondScreenState.INITIAL
import com.example.ryuji_mvvm_architecture.main.fragment.SecondFragment.SecondReceivedType.CLICK_NEXT_BUTTON

class SecondFragment : BaseFragment<MainViewModel, FragmentSecondBinding>(MainViewModel::class.java) {


    enum class SecondReceivedType : ReceivedType {
        CLICK_NEXT_BUTTON
    }

    override val onReceivedMap: Map<ReceivedType, (Any?) -> Unit> = mapOf(
        CLICK_NEXT_BUTTON to { _ -> viewModel.dispatch(SecondScreenState.NEXT) }
    )

    override fun layoutResource() = R.layout.fragment_second

    override fun bindViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { onReceived(CLICK_NEXT_BUTTON) }
        viewModel.dispatch(INITIAL)
    }
}