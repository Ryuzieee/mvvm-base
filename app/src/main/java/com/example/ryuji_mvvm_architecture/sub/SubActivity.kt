package com.example.ryuji_mvvm_architecture.sub

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseActivity
import com.example.ryuji_mvvm_architecture.base.ReceiverType
import com.example.ryuji_mvvm_architecture.databinding.ActivitySubBinding
import com.example.ryuji_mvvm_architecture.util.FragmentTransitionAnimation

class SubActivity : BaseActivity<SubViewModel, ActivitySubBinding>(SubViewModel::class.java) {

    enum class SubReceiverType : ReceiverType {
        ON_BACK_PRESSED,
        UPDATE_TOOLBAR,
        TRANSITION
    }

    override val receiverMap: Map<ReceiverType, (Any?) -> Unit> = mapOf(
        SubReceiverType.ON_BACK_PRESSED to { _ -> onBackPressed() },
        SubReceiverType.UPDATE_TOOLBAR to { parameter -> updateToolbar(parameter as SubTransitionState) },
        SubReceiverType.TRANSITION to { parameter -> transition(parameter as SubTransitionState) }
    )
    override val viewModelProviderFactory: ViewModelProvider.Factory = SubViewModelFactory()

    override fun layoutResource(): Int = R.layout.activity_sub

    override fun firstFragment(): Fragment = SubTransitionState.SUB.fragment

    override fun bindViewModel(viewModel: SubViewModel) {
        binding.viewModel = viewModel
    }

    override fun animation(): FragmentTransitionAnimation? = null

    override fun initialize() {
        binding.apply {
            // これは一度でいいっぽい!!
//            setSupportActionBar(toolbar)
            toolbarBack.setOnClickListener { onReceive(SubReceiverType.ON_BACK_PRESSED) }
        }
        viewModel.subTransitionState.observe(this, Observer<SubTransitionState> {
            onReceive(SubReceiverType.UPDATE_TOOLBAR, it)
            onReceive(SubReceiverType.TRANSITION, it)
        })
        viewModel.dispatch(SubTransitionState.SUB)
    }

    private fun updateToolbar(subTransitionState: SubTransitionState) {
        binding.apply {
            toolbarBack.isVisible = !subTransitionState.isFirstFragment()
        }
    }
}
