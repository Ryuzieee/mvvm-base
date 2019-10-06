package com.example.ryuji_mvvm_architecture.main.fragment

import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.databinding.FragmentFirstBinding
import com.example.ryuji_mvvm_architecture.main.FirstProperty
import com.example.ryuji_mvvm_architecture.main.FirstScreenState
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.base.ReceivedType

class FirstFragment :
    BaseFragment<MainViewModel, FragmentFirstBinding>(MainViewModel::class.java) {

    enum class FirstReceivedType : ReceivedType {
        CLICK_NEXT_BUTTON
    }

    override val onReceivedMap: Map<ReceivedType, (Any?) -> Unit> = mapOf(
        FirstReceivedType.CLICK_NEXT_BUTTON to { _ -> viewModel.dispatch(FirstScreenState.NEXT) }
    )

    override fun layoutResource() = R.layout.fragment_first

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { onReceived(FirstReceivedType.CLICK_NEXT_BUTTON) }
        viewModel.dispatch(FirstScreenState.INITIAL)
    }
}