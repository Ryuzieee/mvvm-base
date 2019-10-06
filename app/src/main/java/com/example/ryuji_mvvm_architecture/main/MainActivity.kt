package com.example.ryuji_mvvm_architecture.main

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseActivity
import com.example.ryuji_mvvm_architecture.databinding.ActivityMainBinding
import com.example.ryuji_mvvm_architecture.util.FragmentTransitionAnimation


class MainActivity :
    BaseActivity<MainViewModel, ActivityMainBinding, MainActivity.MainReceivedType>(MainViewModel::class.java) {

    enum class MainReceivedType {
        ON_BACK_PRESSED,
        UPDATE_TOOLBAR,
        TRANSITION
    }

    override val onReceivedMap: Map<MainReceivedType, (Any?) -> Unit> = mapOf(
        MainReceivedType.ON_BACK_PRESSED to { _ -> onBackPressed() },
        MainReceivedType.UPDATE_TOOLBAR to { parameter -> updateToolbar(parameter as MainTransitionState) },
        MainReceivedType.TRANSITION to { parameter -> transition(parameter as MainTransitionState) }
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
            toolbarBack.setOnClickListener { onReceived(MainReceivedType.ON_BACK_PRESSED) }
        }
        viewModel.mainTransitionState.observe(this, Observer<MainTransitionState> {
            onReceived(MainReceivedType.UPDATE_TOOLBAR, it)
            onReceived(MainReceivedType.TRANSITION, it)
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