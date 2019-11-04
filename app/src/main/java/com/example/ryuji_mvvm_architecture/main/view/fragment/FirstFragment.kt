package com.example.ryuji_mvvm_architecture.main.view.fragment

import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.Data
import com.example.ryuji_mvvm_architecture.base.FragmentScreenState
import com.example.ryuji_mvvm_architecture.databinding.FragmentFirstBinding
import com.example.ryuji_mvvm_architecture.main.FirstScreenState
import com.example.ryuji_mvvm_architecture.main.MainViewModel

class FirstFragment : BaseFragment<MainViewModel, FragmentFirstBinding>() {

    override val layoutResource = R.layout.fragment_first

    override val propertyId: String = FirstScreenState.INITIAL.id()

    override val observerMap: Map<FragmentScreenState, (Data) -> Unit> = emptyMap()

    override fun bindViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { viewModel.dispatch(FirstScreenState.NEXT) }
        viewModel.dispatch(FirstScreenState.INITIAL)
    }
}