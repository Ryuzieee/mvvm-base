package com.example.ryuji_mvvm_architecture.view.activity

import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.databinding.ActivityMainBinding
import com.example.ryuji_mvvm_architecture.view.fragment.MainFragment
import com.example.ryuji_mvvm_architecture.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.activity_main

    override fun firstFragment() = MainFragment()

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }
}