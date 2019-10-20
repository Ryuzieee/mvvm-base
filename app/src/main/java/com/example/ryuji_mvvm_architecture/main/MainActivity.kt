package com.example.ryuji_mvvm_architecture.main

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseActivity
import com.example.ryuji_mvvm_architecture.base.ReceiverType
import com.example.ryuji_mvvm_architecture.databinding.ActivityMainBinding
import com.example.ryuji_mvvm_architecture.main.MainActivity.MainReceiverType.*
import com.example.ryuji_mvvm_architecture.util.FragmentTransitionAnimation

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    enum class MainReceiverType : ReceiverType {
        ON_BACK_PRESSED,
        UPDATE_TOOLBAR,
        TRANSITION
    }

    override val receiverMap: Map<ReceiverType, (Any?) -> Unit> = mapOf(
        ON_BACK_PRESSED to { _ -> onBackPressed() },
        UPDATE_TOOLBAR to { parameter -> updateToolbar(parameter as MainTransitionState) },
        TRANSITION to { parameter -> transition(parameter as MainTransitionState) }
    )

    override val viewModelProviderFactory: ViewModelProvider.Factory = MainViewModelFactory(MainProviderImpl())

    override fun layoutResource() = R.layout.activity_main

    override fun firstFragment() = MainTransitionState.FIRST.fragment

    override fun bindViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun animation() = FragmentTransitionAnimation().rightToLeft()

    override fun initialize() {
        binding.apply {
            setSupportActionBar(toolbar)
            toolbarBack.setOnClickListener { onReceive(ON_BACK_PRESSED) }
        }
        viewModel.mainTransitionState.observe(this, Observer<MainTransitionState> {
            onReceive(UPDATE_TOOLBAR, it)
            onReceive(TRANSITION, it)
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