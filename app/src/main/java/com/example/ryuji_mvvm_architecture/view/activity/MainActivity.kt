package com.example.ryuji_mvvm_architecture.view.activity

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.databinding.ActivityMainBinding
import com.example.ryuji_mvvm_architecture.model.FragmentTransitionAnimation
import com.example.ryuji_mvvm_architecture.state.ParentScreenState
import com.example.ryuji_mvvm_architecture.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.activity_main

    override fun firstFragment() = ParentScreenState.FIRST.fragment

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun animation() = FragmentTransitionAnimation(
        enter = R.anim.slide_in_right,
        exit = R.anim.slide_out_left,
        popEnter = R.anim.slide_in_left,
        popExit = R.anim.slide_out_right
    )

    override fun onBackPressed() {
        viewModel.previousParentScreenState()?.let {
            viewModel.dispatch(it)
        } ?: super.onBackPressed()
    }

    override fun initialize() {
        viewModel.dispatch(ParentScreenState.FIRST)
        binding.apply {
            // TODO: 何故か最初のobserveでの「binding.toolbar.title = parentTransitionState.title」で更新されないため
            toolbar.title = ParentScreenState.FIRST.title
            setSupportActionBar(toolbar)
        }
        viewModel.getParentScreenState().observe(this, Observer<ParentScreenState> { parentTransitionState ->
            binding.toolbar.title = parentTransitionState.title
            // parentTransitionStateのindexが現在のStateよりも小さい場合は画面を戻ると判断しback
            val new = ParentScreenState.values().indexOf(parentTransitionState)
            val old = ParentScreenState.values().indexOfFirst {
                it.fragment == supportFragmentManager.fragments.first()
            }
            if (old > new) {
                back()
            } else {
                transition(parentTransitionState.fragment)
            }
        })
    }

}