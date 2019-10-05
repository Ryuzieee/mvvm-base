package com.example.ryuji_mvvm_architecture.main.fragment

import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.databinding.FragmentSecondBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.SecondProperty
import com.example.ryuji_mvvm_architecture.main.SecondScreenState

class SecondFragment : BaseFragment<MainViewModel, FragmentSecondBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.fragment_second

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { viewModel.dispatch(SecondScreenState.NEXT) }
        viewModel.secondProperty.observe(this, Observer<SecondProperty> {})
        viewModel.dispatch(SecondScreenState.FETCH_FROM_SERVER)
    }
}