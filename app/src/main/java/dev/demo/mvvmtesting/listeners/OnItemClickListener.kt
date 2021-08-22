package dev.demo.mvvmtesting.listeners

import dev.demo.mvvmtesting.models.Drink

interface OnItemClickListener {
    fun onClick(drink: Drink)
}