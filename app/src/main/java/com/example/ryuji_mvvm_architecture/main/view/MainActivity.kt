package com.example.ryuji_mvvm_architecture.main.view

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseActivity
import com.example.ryuji_mvvm_architecture.base.TransitionState
import com.example.ryuji_mvvm_architecture.databinding.ActivityMainBinding
import com.example.ryuji_mvvm_architecture.main.MainTransitionState
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.MainViewModelFactory
import com.example.ryuji_mvvm_architecture.main.provider.MainProviderImpl
import com.example.ryuji_mvvm_architecture.util.FragmentTransitionAnimation

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    override val layoutResource = R.layout.activity_main

    override val firstFragment = MainTransitionState.FIRST.fragment

    override val transitionAnimation = FragmentTransitionAnimation().rightToLeft()

    override val viewModelProviderFactory: ViewModelProvider.Factory = MainViewModelFactory(MainProviderImpl())

    override val observerMap: Map<TransitionState, (TransitionState) -> Unit> = mapOf(
        // TODO:なんか気持ち悪いから直す🤮
        MainTransitionState.FIRST to { transitionState -> updateToolbar(transitionState as MainTransitionState) },
        MainTransitionState.SECOND to { transitionState -> updateToolbar(transitionState as MainTransitionState) },
        MainTransitionState.THIRD to { transitionState -> updateToolbar(transitionState as MainTransitionState) }
    )

    override fun bindViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.apply {
            setSupportActionBar(toolbar)
            toolbarBack.setOnClickListener { onBackPressed() }
        }
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