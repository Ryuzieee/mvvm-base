package com.example.ryuji_mvvm_architecture.main.view.fragment

import android.content.Intent
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.Data
import com.example.ryuji_mvvm_architecture.base.FragmentScreenState
import com.example.ryuji_mvvm_architecture.databinding.FragmentThirdBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.ThirdScreenState
import com.example.ryuji_mvvm_architecture.sub.SubActivity


class ThirdFragment : BaseFragment<MainViewModel, FragmentThirdBinding>() {

    override val propertyId: String = ThirdScreenState.INITIAL.id()

    override val receiverMap: Map<FragmentScreenState, (Data) -> Unit> = mapOf(
        ThirdScreenState.START_NEXT_ACTIVITY to { data -> transitionToSubActivity(data) }
    )

    override val layoutResource = R.layout.fragment_third

    override fun bindViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.signupButton.setOnClickListener { viewModel.dispatch(ThirdScreenState.START_NEXT_ACTIVITY) }
        viewModel.dispatch(ThirdScreenState.INITIAL)
    }

    private fun transitionToSubActivity(data: Data) {
        startActivity(Intent(requireActivity(), SubActivity::class.java))
    }
}