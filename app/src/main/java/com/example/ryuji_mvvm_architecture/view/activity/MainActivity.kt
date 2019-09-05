package com.example.ryuji_mvvm_architecture.view.activity

import android.util.Log
import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.databinding.ActivityMainBinding
import com.example.ryuji_mvvm_architecture.state.MainTransitionState
import com.example.ryuji_mvvm_architecture.view.fragment.MainFragment
import com.example.ryuji_mvvm_architecture.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.activity_main

    override fun firstFragment() = MainTransitionState.MAIN.fragment

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    // TODO: 上手く機能してない!!
    override fun initialize() {
        viewModel.mainTransitionState.observe(this, Observer<MainTransitionState> {
            when (it) {
                MainTransitionState.SECOND -> {
                    Log.d("", "")
                }
            }
        })
    }
}