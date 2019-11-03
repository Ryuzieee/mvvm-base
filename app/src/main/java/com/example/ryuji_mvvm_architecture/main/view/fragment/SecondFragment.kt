package com.example.ryuji_mvvm_architecture.main.view.fragment

import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.Data
import com.example.ryuji_mvvm_architecture.base.FragmentScreenState
import com.example.ryuji_mvvm_architecture.databinding.FragmentSecondBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.SecondScreenState

class SecondFragment : BaseFragment<MainViewModel, FragmentSecondBinding>() {

    override val propertyId: String = SecondScreenState.INITIAL.id()

    override val receiverMap: Map<FragmentScreenState, (Data) -> Unit> = emptyMap()

    override fun layoutResource() = R.layout.fragment_second

    override fun bindViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { viewModel.dispatch(SecondScreenState.NEXT) }
        viewModel.dispatch(SecondScreenState.INITIAL)
    }
}