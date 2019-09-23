package com.example.ryuji_mvvm_architecture.view.fragment

import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.databinding.FragmentThirdBinding
import com.example.ryuji_mvvm_architecture.state.ThirdScreenState
import com.example.ryuji_mvvm_architecture.state.ThirdState
import com.example.ryuji_mvvm_architecture.viewmodel.MainViewModel

class ThirdFragment : BaseFragment<MainViewModel, FragmentThirdBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.fragment_third

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.signupButton.setOnClickListener {
            viewModel.dispatch(ThirdScreenState.BACK)
        }
        viewModel.getThirdState().observe(this, Observer<ThirdState> { thirdState ->
            // TODO: 連鎖処置など
        })
    }
}