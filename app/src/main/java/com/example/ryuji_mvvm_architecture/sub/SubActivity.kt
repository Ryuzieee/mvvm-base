package com.example.ryuji_mvvm_architecture.sub

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseActivity
import com.example.ryuji_mvvm_architecture.base.TransitionState
import com.example.ryuji_mvvm_architecture.databinding.ActivitySubBinding
import com.example.ryuji_mvvm_architecture.util.FragmentTransitionAnimation

class SubActivity : BaseActivity<SubViewModel, ActivitySubBinding>(SubViewModel::class.java) {

    override val receiverMap: Map<TransitionState, (TransitionState) -> Unit> = mapOf(
        SubTransitionState.SUB to { transitionState -> updateToolbar(transitionState as SubTransitionState) }
    )

    override val viewModelProviderFactory: ViewModelProvider.Factory = SubViewModelFactory()

    override val layoutResource: Int = R.layout.activity_sub

    override val firstFragment: Fragment = SubTransitionState.SUB.fragment

    override fun bindViewModel(viewModel: SubViewModel) {
        binding.viewModel = viewModel
    }

    override val transitionAnimation: FragmentTransitionAnimation? = null

    override fun initialize() {
        binding.apply {
            // これは一度でいいっぽい!!
//            setSupportActionBar(toolbar)
            toolbarBack.setOnClickListener { onBackPressed() }
        }
        viewModel.dispatch(SubTransitionState.SUB)
    }

    private fun updateToolbar(subTransitionState: SubTransitionState) {
        binding.apply {
            toolbarBack.isVisible = !subTransitionState.isFirstFragment()
        }
    }
}
