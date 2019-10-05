package com.example.ryuji_mvvm_architecture.main

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseActivity
import com.example.ryuji_mvvm_architecture.base.TransitionState
import com.example.ryuji_mvvm_architecture.databinding.ActivityMainBinding
import com.example.ryuji_mvvm_architecture.util.ClickType
import com.example.ryuji_mvvm_architecture.util.FragmentTransitionAnimation


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.activity_main

    override fun firstFragment() = ParentScreenState.FIRST.fragment

    override val onClickedMap: Map<ClickType, () -> Unit> = mapOf(
        ClickType.TOOL_BAR_BACK to { onBackPressed() }
    )

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun animation() = FragmentTransitionAnimation().rightToLeft()

    override fun initialize() {
        binding.apply {
            setSupportActionBar(toolbar)
            toolbarBack.setOnClickListener { onClicked(ClickType.TOOL_BAR_BACK) }
        }
        viewModel.transitionState.observe(this, Observer<TransitionState> {
            it as ParentScreenState
            updateToolbar(it)
            if (viewModel.isBack(supportFragmentManager)) back() else transition(it.fragment)
        })
        viewModel.dispatch(ParentScreenState.FIRST)
    }

    private fun updateToolbar(parentScreenState: ParentScreenState) {
        binding.apply {
            toolbarBack.isVisible = parentScreenState.fragment != firstFragment()
            toolbarTitle.text = parentScreenState.title
            ObjectAnimator.ofInt(toolbarProgress, "progress", parentScreenState.progress).run {
                duration = 500
                interpolator = DecelerateInterpolator()
                start()
            }
        }
    }

}