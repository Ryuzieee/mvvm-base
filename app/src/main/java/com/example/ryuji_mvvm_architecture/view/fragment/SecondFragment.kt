package com.example.ryuji_mvvm_architecture.view.fragment

import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.databinding.FragmentSecondBinding
import com.example.ryuji_mvvm_architecture.state.SecondScreenState
import com.example.ryuji_mvvm_architecture.state.SecondState
import com.example.ryuji_mvvm_architecture.viewmodel.MainViewModel

class SecondFragment : BaseFragment<MainViewModel, FragmentSecondBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.fragment_second

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.apply {
            nextButton.setOnClickListener {
                viewModel?.dispatch(SecondScreenState.NEXT)
            }
        }
        viewModel.getSecondState().observe(this, Observer<SecondState> { secondState ->
            // TODO: 連鎖処置など
        })
    }
}