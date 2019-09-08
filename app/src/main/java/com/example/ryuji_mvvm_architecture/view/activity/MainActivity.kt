package com.example.ryuji_mvvm_architecture.view.activity

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.databinding.ActivityMainBinding
import com.example.ryuji_mvvm_architecture.state.ParentScreenState
import com.example.ryuji_mvvm_architecture.viewmodel.MainViewModel

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.activity_main

    override fun firstFragment() = ParentScreenState.FIRST.fragment

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun transitionAnimation() = true

    override fun initialize() {

        binding.apply {
            setSupportActionBar(toolbar)
            backButton.setOnClickListener { back() }
        }

        viewModel.parentScreenState.observe(this, Observer<ParentScreenState> { mainTransitionState ->
            val new = ParentScreenState.values().indexOf(mainTransitionState)
            val old = ParentScreenState.values().indexOfFirst {
                it.fragment == supportFragmentManager.fragments.first()
            }
            if (old > new) {
                back()
            } else {
                transition(mainTransitionState.fragment)
            }
        })

        viewModel.progressState.observe(this, Observer<ParentScreenState> { mainTransitionState ->
            binding.apply {
                backButton.isVisible = mainTransitionState.fragment != firstFragment()
                pageTitle.text = mainTransitionState.title
                ObjectAnimator.ofInt(pageProgress, "progress", mainTransitionState.progress).run {
                    duration = 500
                    interpolator = DecelerateInterpolator()
                    start()
                }
            }
        })
    }

}