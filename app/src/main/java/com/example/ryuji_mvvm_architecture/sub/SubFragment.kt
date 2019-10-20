package com.example.ryuji_mvvm_architecture.sub

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.ReceiverType
import com.example.ryuji_mvvm_architecture.databinding.FragmentSubBinding
import com.example.ryuji_mvvm_architecture.sub.recyclerView.CoupleListAdapter


class SubFragment : BaseFragment<SubViewModel, FragmentSubBinding>() {

    override val receiverMap: Map<ReceiverType, (Any?) -> Unit> = emptyMap()

    override fun layoutResource() = com.example.ryuji_mvvm_architecture.R.layout.fragment_sub

    override fun bindViewModel(viewModel: SubViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        viewModel.dispatch(SubScreenState.INITIAL)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = CoupleListAdapter(context, listOf("", "", "", "", "", "", "", "", "", ""))
        }
    }
}