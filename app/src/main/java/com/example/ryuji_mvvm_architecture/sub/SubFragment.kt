package com.example.ryuji_mvvm_architecture.sub

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ryuji_mvvm_architecture.base.BaseFragment
import com.example.ryuji_mvvm_architecture.base.ReceiverType
import com.example.ryuji_mvvm_architecture.databinding.FragmentSubBinding
import com.example.ryuji_mvvm_architecture.sub.recyclerView.RecyclerViewDataAdapter
import com.example.ryuji_mvvm_architecture.sub.recyclerView.SectionDataModel
import com.example.ryuji_mvvm_architecture.sub.recyclerView.SectionListDataAdapter
import com.example.ryuji_mvvm_architecture.sub.recyclerView.SingleItemModel


class SubFragment : BaseFragment<SubViewModel, FragmentSubBinding>() {

    override val receiverMap: Map<ReceiverType, (Any?) -> Unit> = emptyMap()

    override fun layoutResource() = com.example.ryuji_mvvm_architecture.R.layout.fragment_sub

    override fun bindViewModel(viewModel: SubViewModel) {
        binding.viewModel = viewModel
    }

    override fun initialize() {
        viewModel.dispatch(SubScreenState.INITIAL)

        createDummyData()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())

            val recyclerViewDataAdapter = SectionListDataAdapter(context, allSampleData[0].allItemsInSection)
//            val recyclerViewDataAdapter = RecyclerViewDataAdapter(context, allSampleData)

            adapter = recyclerViewDataAdapter
        }
    }

    val allSampleData = ArrayList<SectionDataModel>()

    fun createDummyData() {
        for (i in 1..5) {
            val sectionDataModel = SectionDataModel()

            val singleItem = ArrayList<SingleItemModel>()
            for (j in 0..5) {
                singleItem.add(SingleItemModel("Item $j", "URL $j"))
            }

            sectionDataModel.allItemsInSection = singleItem

            allSampleData.add(sectionDataModel)

        }
    }
}