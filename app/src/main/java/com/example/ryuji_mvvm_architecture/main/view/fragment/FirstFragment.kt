package com.example.ryuji_mvvm_architecture.main.view.fragment

import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.FragmentScreenState
import com.example.ryuji_mvvm_architecture.base.ScreenData
import com.example.ryuji_mvvm_architecture.databinding.FragmentFirstBinding
import com.example.ryuji_mvvm_architecture.main.FirstActionState
import com.example.ryuji_mvvm_architecture.main.FirstFragmentScreenState
import com.example.ryuji_mvvm_architecture.main.FirstScreenData
import com.example.ryuji_mvvm_architecture.main.viewModel.MainViewModel

class FirstFragment : BaseFragment<MainViewModel, FragmentFirstBinding>() {

    override val layoutResource = R.layout.fragment_first

    override val propertyId: String = FirstFragmentScreenState.NONE.id()

    override val observerMap: Map<FragmentScreenState, (ScreenData) -> Unit> = mapOf(
        FirstFragmentScreenState.INITIALIZED to ::initialized
    )

    override fun bindViewModel(viewModel: MainViewModel) {
        this.binding.viewModel = viewModel
    }

    override fun initialize() {
        this.binding.nextButton.setOnClickListener { this.onAction(FirstActionState.ON_TAPPED_NEXT) }
        this.onAction(FirstActionState.ON_INITIALIZE)
    }

    private fun initialized(screenData: ScreenData) {
        val data = screenData as FirstScreenData
        this.binding.nextButton.text = data.text
    }

}