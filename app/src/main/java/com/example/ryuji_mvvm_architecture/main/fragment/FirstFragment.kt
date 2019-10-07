package com.example.ryuji_mvvm_architecture.main.fragment

import com.example.moeidbannerlibrary.banner.BaseBannerAdapter
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.ReceiverType
import com.example.ryuji_mvvm_architecture.databinding.FragmentFirstBinding
import com.example.ryuji_mvvm_architecture.main.FirstScreenState.INITIAL
import com.example.ryuji_mvvm_architecture.main.FirstScreenState.NEXT
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.fragment.FirstFragment.FirstReceiverType.CLICK_NEXT_BUTTON

class FirstFragment : BaseFragment<MainViewModel, FragmentFirstBinding>(MainViewModel::class.java) {

    enum class FirstReceiverType : ReceiverType {
        CLICK_NEXT_BUTTON
    }

    override val receiverMap: Map<ReceiverType, (Any?) -> Unit> = mapOf(
        CLICK_NEXT_BUTTON to { _ -> viewModel.dispatch(NEXT) }
    )

    override fun layoutResource() = com.example.ryuji_mvvm_architecture.R.layout.fragment_first

    override fun bindViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { onReceive(CLICK_NEXT_BUTTON) }
        viewModel.dispatch(INITIAL)

        // TODO: カルーセル出してみる!!
        val urls = mutableListOf<String>(
            "https://raw.githubusercontent.com/AndroidCodility/StaggeredRecyclerView/master/images/one.jpg",
            "https://raw.githubusercontent.com/AndroidCodility/StaggeredRecyclerView/master/images/nine.jpg",
            "https://raw.githubusercontent.com/AndroidCodility/StaggeredRecyclerView/master/images/ten.jpg"
        )
        val bannerAdapter = BaseBannerAdapter(activity, urls)
        bannerAdapter.setOnBannerItemClickListener { }
        binding.banner.apply {
            setAdapter(bannerAdapter)
            setAutoPlayDuration(3000)
        }
    }
}