package com.example.ryuji_mvvm_architecture.view.fragment

import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.databinding.FragmentFirstBinding
import com.example.ryuji_mvvm_architecture.state.FirstScreenState
import com.example.ryuji_mvvm_architecture.state.FirstState
import com.example.ryuji_mvvm_architecture.viewmodel.MainViewModel

class FirstFragment : BaseFragment<MainViewModel, FragmentFirstBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.fragment_first

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.apply {
            nextButton.setOnClickListener {
                viewModel?.dispatch(FirstScreenState.NEXT)
            }
            // TODO: おまけ
            fetchButton.setOnClickListener {
                viewModel?.dispatch(FirstScreenState.FETCH)
            }
        }
        viewModel.getFirstState().observe(this, Observer<FirstState> { firstState ->
            // TODO: 連鎖処置など
        })
        viewModel.dispatch(FirstScreenState.INITIALIZE)
    }
}