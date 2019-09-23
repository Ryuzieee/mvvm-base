package com.example.ryuji_mvvm_architecture.view.fragment

import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.databinding.FragmentFirstBinding
import com.example.ryuji_mvvm_architecture.state.FirstScreenState
import com.example.ryuji_mvvm_architecture.viewmodel.MainViewModel

class FirstFragment : BaseFragment<MainViewModel, FragmentFirstBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.fragment_first

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        viewModel.dispatch(FirstScreenState.INITIALIZE)
        binding.apply {
            nextButton.setOnClickListener {
                viewModel?.dispatch(FirstScreenState.NEXT)
            }
        }

        // TODO: おまけ
        binding.apply {
            fetchButton.setOnClickListener {
                viewModel?.dispatch(FirstScreenState.FETCH)
            }
        }
    }
}