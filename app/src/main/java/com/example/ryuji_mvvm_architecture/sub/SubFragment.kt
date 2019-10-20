package com.example.ryuji_mvvm_architecture.sub

import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.ReceiverType
import com.example.ryuji_mvvm_architecture.databinding.FragmentSubBinding

class SubFragment : BaseFragment<SubViewModel, FragmentSubBinding>() {

    override val receiverMap: Map<ReceiverType, (Any?) -> Unit> = emptyMap()

    override fun layoutResource() = com.example.ryuji_mvvm_architecture.R.layout.fragment_sub

    override fun bindViewModel(viewModel: SubViewModel) {
        binding.viewModel = viewModel as SubViewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener {
            // Nothing
        }
        viewModel.dispatch(SubScreenState.INITIAL)
    }
}