package com.example.ryuji_mvvm_architecture.view.fragment

import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.databinding.FragmentMainBinding
import com.example.ryuji_mvvm_architecture.state.MainScreenState
import com.example.ryuji_mvvm_architecture.viewmodel.MainViewModel

class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.fragment_main

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        viewModel.dispatch(MainScreenState.INITIALIZE)


        // TODO: Test
        binding.apply {
            fetchButton.setOnClickListener {
                viewModel?.dispatch(MainScreenState.FETCH)
            }
            nextButton.setOnClickListener {
                viewModel?.dispatch(MainScreenState.FINISH)
            }
        }
    }
}