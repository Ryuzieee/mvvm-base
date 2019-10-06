package com.example.ryuji_mvvm_architecture.main

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseActivity
import com.example.ryuji_mvvm_architecture.base.TransitionState
import com.example.ryuji_mvvm_architecture.databinding.ActivityMainBinding
import com.example.ryuji_mvvm_architecture.util.FragmentTransitionAnimation
import com.example.ryuji_mvvm_architecture.util.ReceivedType


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    override val onReceivedMap: Map<ReceivedType, (() -> Unit)> = mapOf(
        ReceivedType.CLICK_TOOL_BAR_BACK to { onBackPressed() }
    )

    override fun layoutResource() = R.layout.activity_main

    override fun firstFragment() = MainTransitionState.FIRST.fragment

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun animation() = FragmentTransitionAnimation().rightToLeft()

    override fun initialize() {
        binding.apply {
            setSupportActionBar(toolbar)
            toolbarBack.setOnClickListener { onReceived(ReceivedType.CLICK_TOOL_BAR_BACK) }
        }
        viewModel.mainTransitionState.observe(this, Observer<MainTransitionState> {
            updateToolbar(it)
            transition(it)
        })
        viewModel.dispatch(MainTransitionState.FIRST)
    }

    private fun updateToolbar(mainTransitionState: MainTransitionState) {
        binding.apply {
            toolbarBack.isVisible = !mainTransitionState.isFirstFragment()
            toolbarTitle.text = mainTransitionState.title
            ObjectAnimator.ofInt(toolbarProgress, "progress", mainTransitionState.progress).run {
                duration = 500
                interpolator = DecelerateInterpolator()
                start()
            }
        }
    }

}