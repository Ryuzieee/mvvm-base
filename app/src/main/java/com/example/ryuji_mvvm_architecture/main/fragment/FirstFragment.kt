package com.example.ryuji_mvvm_architecture.main.fragment

import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.databinding.FragmentFirstBinding
import com.example.ryuji_mvvm_architecture.main.FirstProperty
import com.example.ryuji_mvvm_architecture.main.FirstScreenState
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.util.ReceivedType

class FirstFragment : BaseFragment<MainViewModel, FragmentFirstBinding>(MainViewModel::class.java) {

    override val onReceivedMap: Map<ReceivedType, () -> Unit> = mapOf(
        ReceivedType.CLICK_NEXT_BUTTON_1 to { viewModel.dispatch(FirstScreenState.NEXT) }
    )

    override fun layoutResource() = R.layout.fragment_first

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { onReceived(ReceivedType.CLICK_NEXT_BUTTON_1) }
        viewModel.firstProperty.observe(viewLifecycleOwner, Observer<FirstProperty> {})
        viewModel.dispatch(FirstScreenState.INITIALIZE)
    }
}