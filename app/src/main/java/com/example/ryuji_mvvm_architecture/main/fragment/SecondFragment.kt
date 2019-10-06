package com.example.ryuji_mvvm_architecture.main.fragment

import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.databinding.FragmentSecondBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.SecondProperty
import com.example.ryuji_mvvm_architecture.main.SecondScreenState
import com.example.ryuji_mvvm_architecture.base.ReceivedType

class SecondFragment :
    BaseFragment<MainViewModel, FragmentSecondBinding>(MainViewModel::class.java) {


    enum class SecondReceivedType : ReceivedType {
        CLICK_NEXT_BUTTON
    }

    override val onReceivedMap: Map<ReceivedType, (Any?) -> Unit> = mapOf(
        SecondReceivedType.CLICK_NEXT_BUTTON to { _ -> viewModel.dispatch(SecondScreenState.NEXT) }
    )

    override fun layoutResource() = R.layout.fragment_second

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { onReceived(SecondReceivedType.CLICK_NEXT_BUTTON) }
        viewModel.dispatch(SecondScreenState.INITIAL)
    }
}