package com.example.ryuji_mvvm_architecture.main

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseActivity
import com.example.ryuji_mvvm_architecture.base.ReceivedType
import com.example.ryuji_mvvm_architecture.databinding.ActivityMainBinding
import com.example.ryuji_mvvm_architecture.main.MainActivity.MainReceivedType.*
import com.example.ryuji_mvvm_architecture.main.MainTransitionState.FIRST
import com.example.ryuji_mvvm_architecture.util.FragmentTransitionAnimation

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    enum class MainReceivedType : ReceivedType {
        ON_BACK_PRESSED,
        UPDATE_TOOLBAR,
        TRANSITION
    }

    override val onReceivedMap: Map<ReceivedType, (Any?) -> Unit> = mapOf(
        ON_BACK_PRESSED to { _ -> onBackPressed() },
        UPDATE_TOOLBAR to { parameter -> updateToolbar(parameter as MainTransitionState) },
        TRANSITION to { parameter -> transition(parameter as MainTransitionState) }
    )

    override fun layoutResource() = R.layout.activity_main

    override fun firstFragment() = FIRST.fragment

    override fun bindViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun animation() = FragmentTransitionAnimation().rightToLeft()

    override fun initialize() {
        binding.apply {
            setSupportActionBar(toolbar)
            toolbarBack.setOnClickListener { onReceived(ON_BACK_PRESSED) }
        }
        viewModel.mainTransitionState.observe(this, Observer<MainTransitionState> {
            onReceived(UPDATE_TOOLBAR, it)
            onReceived(TRANSITION, it)
        })
        viewModel.dispatch(FIRST)
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