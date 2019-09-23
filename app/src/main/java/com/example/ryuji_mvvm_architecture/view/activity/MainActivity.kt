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

    override fun animation() = FragmentTransitionAnimation().rightToLeft()

    override fun onBackPressed() {
        viewModel.previousParentScreenState()?.let {
            viewModel.dispatch(it)
        } ?: super.onBackPressed()
    }

    override fun initialize() {
        binding.apply {
            setSupportActionBar(toolbar)
            toolbarBack.setOnClickListener { onBackPressed() }
        }
        viewModel.getParentScreenState().observe(this, Observer<ParentScreenState> { parentScreenState ->
            updateToolbar(parentScreenState)
            if (viewModel.isBack(supportFragmentManager)) back() else transition(parentScreenState.fragment)
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