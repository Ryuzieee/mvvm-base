package com.example.ryuji_mvvm_architecture.main.view.fragment

import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.FragmentScreenState
import com.example.ryuji_mvvm_architecture.base.ScreenData
import com.example.ryuji_mvvm_architecture.databinding.FragmentSecondBinding
import com.example.ryuji_mvvm_architecture.main.SecondActionState
import com.example.ryuji_mvvm_architecture.main.SecondFragmentScreenState
import com.example.ryuji_mvvm_architecture.main.SecondScreenData
import com.example.ryuji_mvvm_architecture.main.viewModel.MainViewModel

class SecondFragment : BaseFragment<MainViewModel, FragmentSecondBinding>() {

    override val layoutResource = R.layout.fragment_second

    override val propertyId: String = SecondFragmentScreenState.NONE.id()

    override val observerMap: Map<FragmentScreenState, (ScreenData) -> Unit> = mapOf(
        SecondFragmentScreenState.INITIALIZED to ::initialized
    )

    override fun bindViewModel(viewModel: MainViewModel) {
        this.binding.viewModel = viewModel
    }

    override fun initialize() {
        this.binding.backButton.setOnClickListener { this.onAction(SecondActionState.ON_TAPPED_BACK) }
        this.onAction(SecondActionState.ON_INITIALIZE)
    }

    private fun initialized(screenData: ScreenData) {
        val data = screenData as SecondScreenData
        this.binding.backButton.text = data.text
    }

}