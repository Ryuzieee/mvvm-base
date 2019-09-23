package com.example.ryuji_mvvm_architecture.model

import com.example.ryuji_mvvm_architecture.R

data class FragmentTransitionAnimation(
    val enter: Int? = 0,
    val exit: Int? = 0,
    val popEnter: Int? = 0,
    val popExit: Int? = 0
) {
    fun rightToLeft() = FragmentTransitionAnimation(
        enter = R.anim.slide_in_right,
        exit = R.anim.slide_out_left,
        popEnter = R.anim.slide_in_left,
        popExit = R.anim.slide_out_right
    )
}