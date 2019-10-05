package com.example.ryuji_mvvm_architecture.main.fragment

import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.databinding.FragmentSecondBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.SecondProperty
import com.example.ryuji_mvvm_architecture.main.SecondScreenState
import com.example.ryuji_mvvm_architecture.util.ClickType

class SecondFragment : BaseFragment<MainViewModel, FragmentSecondBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.fragment_second

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override val onClickedMap: Map<ClickType, () -> Unit> = mapOf(
        ClickType.BACK_BUTTON_1 to { viewModel.dispatch(SecondScreenState.NEXT) }
    )

    override fun initialize() {
        binding.nextButton.setOnClickListener { onClicked(ClickType.BACK_BUTTON_1) }
        viewModel.secondProperty.observe(this, Observer<SecondProperty> {})
        viewModel.dispatch(SecondScreenState.FETCH_FROM_SERVER)
    }
}