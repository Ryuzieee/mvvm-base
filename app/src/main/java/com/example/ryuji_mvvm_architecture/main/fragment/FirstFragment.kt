package com.example.ryuji_mvvm_architecture.main.fragment

import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.ReceiverType
import com.example.ryuji_mvvm_architecture.databinding.FragmentFirstBinding
import com.example.ryuji_mvvm_architecture.main.FirstScreenState.INITIAL
import com.example.ryuji_mvvm_architecture.main.FirstScreenState.NEXT
import com.example.ryuji_mvvm_architecture.main.MainViewModel
import com.example.ryuji_mvvm_architecture.main.MyAdapter
import com.example.ryuji_mvvm_architecture.main.Version
import com.example.ryuji_mvvm_architecture.main.fragment.FirstFragment.FirstReceiverType.CLICK_NEXT_BUTTON
import com.example.ryuji_mvvm_architecture.util.Utility

class FirstFragment : BaseFragment<MainViewModel, FragmentFirstBinding>(MainViewModel::class.java) {

    enum class FirstReceiverType : ReceiverType {
        CLICK_NEXT_BUTTON
    }

    override val receiverMap: Map<ReceiverType, (Any?) -> Unit> = mapOf(
        CLICK_NEXT_BUTTON to { _ -> viewModel.dispatch(NEXT) }
    )

    override fun layoutResource() = R.layout.fragment_first

    override fun bindViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        binding.nextButton.setOnClickListener { onReceive(CLICK_NEXT_BUTTON) }
        viewModel.dispatch(INITIAL)

        // TODO: カルーセル出してみる!!
        // https://github.com/AndroidCodility/HorizontalRecyclerview
        // https://stackoverflow.com/questions/52590517/horizontal-recyclerview-has-item-cardview-bottom-textview-part-not-fully-display
        context?.let {
            if (Utility.isOnline(it)) {
                val version = ArrayList<Version>()
                version.addAll(Version.getList())
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(it, LinearLayout.HORIZONTAL, false)
                    adapter = MyAdapter(version)
                }
            } else {
                Toast.makeText(it, "No internet available..!!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}