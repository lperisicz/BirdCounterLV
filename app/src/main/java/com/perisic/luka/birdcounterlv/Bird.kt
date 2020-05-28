package com.perisic.luka.birdcounterlv

import androidx.annotation.ColorRes

class Bird(
    val name: String,
    @ColorRes val color: Int,
    var count: Int = 0
)