package com.example.ryuji_mvvm_architecture.main.view

import androidx.lifecycle.ViewModelProvider
import com.example.ryuji_mvvm_architecture.R
import com.example.ryuji_mvvm_architecture.base.BaseActivity
import com.example.ryuji_mvvm_architecture.base.TransitionState
import com.example.ryuji_mvvm_architecture.databinding.ActivityMainBinding
import com.example.ryuji_mvvm_architecture.main.MainTransitionState
import com.example.ryuji_mvvm_architecture.main.provider.MainProviderImpl
import com.example.ryuji_mvvm_architecture.main.viewModel.MainViewModel
import com.example.ryuji_mvvm_architecture.main.viewModel.MainViewModelFactory
import com.example.ryuji_mvvm_architecture.util.FragmentTransitionAnimation

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    MainViewModel::class.java
) {

    override val layoutResource = R.layout.activity_main

    override val firstFragment = MainTransitionState.FIRST.fragment

    override val transitionAnimation = FragmentTransitionAnimation().rightToLeft()

    override val viewModelProviderFactory: ViewModelProvider.Factory =
        MainViewModelFactory(MainProviderImpl())

    override val observerMap: Map<TransitionState, (TransitionState) -> Unit> = mapOf(
        MainTransitionState.FIRST to ::transitionToFirst,
        MainTransitionState.SECOND to ::transitionToSecond
    )

    override fun bindViewModel(viewModel: MainViewModel) {
        this.binding.viewModel = viewModel
    }

    override fun initialize() {
        this.binding.apply {
            setSupportActionBar(toolbar)
            this.toolbarBack.setOnClickListener { onBackPressed() }
        }
    }

    override fun onBackPressed() {
        // バックキー無効化
    }

    /**
     * TODO:これらを整理する
     *  ・TransitionStateの責務を明確にする
     *  ・TransitionStateはFragmentを持つべきなのか検討する
     *  ・画面要件によっては「戻る＝前画面」ではなかったりする
     *  ・BaseActivityに持たせるのは今くらいで良いかも
     */
    private fun transitionToFirst(transitionState: TransitionState) {
        this.removeFragment()
    }

    private fun transitionToSecond(transitionState: TransitionState) {
        this.createOrReplaceFragment(transitionState.fragment)
    }

}