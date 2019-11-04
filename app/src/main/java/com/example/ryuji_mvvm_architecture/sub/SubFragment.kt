package com.example.ryuji_mvvm_architecture.sub

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.Data
import com.example.ryuji_mvvm_architecture.base.FragmentScreenState
import com.example.ryuji_mvvm_architecture.databinding.FragmentSubBinding
import com.example.ryuji_mvvm_architecture.sub.recyclerView.SubListAdapter


class SubFragment : BaseFragment<SubViewModel, FragmentSubBinding>() {

    override val layoutResource = R.layout.fragment_sub

    override val propertyId: String = SubScreenState.INITIAL.id()

    override val observerMap: Map<FragmentScreenState, (Data) -> Unit> = emptyMap()

    private val subListAdapter: SubListAdapter by lazy {
        SubListAdapter(requireContext(), listOf("", "", "", "", "", "", "", "", "", ""))
    }

    override fun bindViewModel(viewModel: SubViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        viewModel.dispatch(SubScreenState.INITIAL)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = subListAdapter
        }
    }
}