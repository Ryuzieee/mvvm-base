package com.example.ryuji_mvvm_architecture.main.fragment

import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.databinding.FragmentSecondBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.SecondProperty
import com.example.ryuji_mvvm_architecture.main.SecondScreenState

class SecondFragment :
    BaseFragment<MainViewModel, FragmentSecondBinding, SecondFragment.SecondReceivedType>(MainViewModel::class.java) {


    enum class SecondReceivedType {
        CLICK_NEXT_BUTTON
    }

    override val onReceivedMap: Map<SecondReceivedType, (Any?) -> Unit> = mapOf(
        SecondReceivedType.CLICK_NEXT_BUTTON to { _ -> viewModel.dispatch(SecondScreenState.NEXT) }
    )

    override fun layoutResource() = R.layout.fragment_second

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { onReceived(SecondReceivedType.CLICK_NEXT_BUTTON) }
        viewModel.secondProperty.observe(viewLifecycleOwner, Observer<SecondProperty> {})
        viewModel.dispatch(SecondScreenState.FETCH_FROM_SERVER)
    }
}