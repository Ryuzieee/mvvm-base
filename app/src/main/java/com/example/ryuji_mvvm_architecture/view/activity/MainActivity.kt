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
        setSupportActionBar(binding.toolbar)
        binding.toolbarBack.setOnClickListener { onBackPressed() }
        viewModel.getParentScreenState().observe(this, Observer<ParentScreenState> { parentScreenState ->
            updateToolbar(parentScreenState)
            // parentTransitionStateのindexが現在のStateよりも小さい場合は画面を戻ると判断しback
            val new = ParentScreenState.values().indexOf(parentScreenState)
            val old = ParentScreenState.values().indexOfFirst {
                it.fragment == supportFragmentManager.fragments.first()
            }
            if (old > new) {
                back()
            } else {
                transition(parentScreenState.fragment)
            }
        })
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