package com.example.ryuji_mvvm_architecture.main.fragment

import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.databinding.FragmentFirstBinding
import com.example.ryuji_mvvm_architecture.main.FirstProperty
import com.example.ryuji_mvvm_architecture.main.FirstScreenState
import com.example.ryuji_mvvm_architecture.main.MainViewModel

class FirstFragment :
    BaseFragment<MainViewModel, FragmentFirstBinding, FirstFragment.FirstReceivedType>(MainViewModel::class.java) {

    enum class FirstReceivedType {
        CLICK_NEXT_BUTTON
    }

    override val onReceivedMap: Map<FirstReceivedType, (Any?) -> Unit> = mapOf(
        FirstReceivedType.CLICK_NEXT_BUTTON to { _ -> viewModel.dispatch(FirstScreenState.NEXT) }
    )

    override fun layoutResource() = R.layout.fragment_first

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { onReceived(FirstReceivedType.CLICK_NEXT_BUTTON) }
        viewModel.firstProperty.observe(viewLifecycleOwner, Observer<FirstProperty> {})
        viewModel.dispatch(FirstScreenState.INITIALIZE)
    }
}