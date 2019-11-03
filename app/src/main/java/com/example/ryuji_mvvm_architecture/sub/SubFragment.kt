package com.example.ryuji_mvvm_architecture.sub

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.Data
import com.example.ryuji_mvvm_architecture.base.FragmentScreenState
import com.example.ryuji_mvvm_architecture.databinding.FragmentSubBinding
import com.example.ryuji_mvvm_architecture.sub.recyclerView.CoupleListAdapter


class SubFragment : BaseFragment<SubViewModel, FragmentSubBinding>() {

    override val propertyId: String = SubScreenState.INITIAL.id()

    private val coupleListAdapter: CoupleListAdapter by lazy {
        CoupleListAdapter(requireContext(), listOf("", "", "", "", "", "", "", "", "", ""))
    }
    override val receiverMap: Map<FragmentScreenState, (Data) -> Unit> = emptyMap()

    override fun layoutResource() = com.example.ryuji_mvvm_architecture.R.layout.fragment_sub

    override fun bindViewModel(viewModel: SubViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        viewModel.dispatch(SubScreenState.INITIAL)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = coupleListAdapter
        }
    }
}