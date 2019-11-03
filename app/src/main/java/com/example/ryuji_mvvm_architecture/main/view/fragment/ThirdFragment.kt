package com.example.ryuji_mvvm_architecture.main.view.fragment

import android.content.Intent
import androidx.lifecycle.Observer
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.ReceiverType
import com.example.ryuji_mvvm_architecture.databinding.FragmentThirdBinding
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.ThirdProperty
import com.example.ryuji_mvvm_architecture.main.ThirdScreenState
import com.example.ryuji_mvvm_architecture.sub.SubActivity


class ThirdFragment : BaseFragment<MainViewModel, FragmentThirdBinding>() {

    enum class ThirdReceiverType : ReceiverType {
        START_NEXT_ACTIVITY
    }

    override val receiverMap: Map<ReceiverType, (Any?) -> Unit> = mapOf(
        ThirdReceiverType.START_NEXT_ACTIVITY to { _ -> viewModel.dispatch(ThirdScreenState.START_NEXT_ACTIVITY) }
    )

    override fun layoutResource() = com.example.ryuji_mvvm_architecture.R.layout.fragment_third

    override fun bindViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.signupButton.setOnClickListener { onReceive(ThirdReceiverType.START_NEXT_ACTIVITY) }
        viewModel.dispatch(ThirdScreenState.INITIAL)
        viewModel.thirdProperty.observe(this, Observer<ThirdProperty> {
            if (it.fragmentScreenState == ThirdScreenState.START_NEXT_ACTIVITY) {
                startActivity(Intent(requireActivity(), SubActivity::class.java))
            }
        })
    }
}