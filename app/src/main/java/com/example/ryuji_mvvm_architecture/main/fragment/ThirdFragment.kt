package com.example.ryuji_mvvm_architecture.main.fragment

import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.databinding.FragmentThirdBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.ThirdProperty
import com.example.ryuji_mvvm_architecture.main.ThirdScreenState
import com.example.ryuji_mvvm_architecture.util.ClickType

class ThirdFragment : BaseFragment<MainViewModel, FragmentThirdBinding>(MainViewModel::class.java) {

    override fun layoutResource() = R.layout.fragment_third

    override fun initializeViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override val onClickedMap: Map<ClickType, () -> Unit> = mapOf(
        ClickType.BACK_BUTTON_1 to { viewModel.dispatch(ThirdScreenState.BACK) }
    )

    override fun initialize() {
        binding.signupButton.setOnClickListener { onClicked(ClickType.BACK_BUTTON_1) }
        viewModel.thirdProperty.observe(this, Observer<ThirdProperty> {})
        viewModel.dispatch(ThirdScreenState.INITIAL)
    }
}