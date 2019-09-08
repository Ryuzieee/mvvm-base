package com.example.ryuji_mvvm_architecture.view.fragment

import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.databinding.FragmentThirdBinding
import com.example.ryuji_mvvm_architecture.state.ThirdScreenState
import com.example.ryuji_mvvm_architecture.viewmodel.MainViewModel

class ThirdFragment : BaseFragment<MainViewModel, FragmentThirdBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.fragment_third

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.apply {
            finishButton.setOnClickListener {
                viewModel?.dispatch(ThirdScreenState.FINISH)
            }
        }
    }
}