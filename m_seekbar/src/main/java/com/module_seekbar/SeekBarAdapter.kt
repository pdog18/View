package com.module_seekbar


import android.view.View

abstract class SeekBarAdapter<T : Any>(private var items: List<T>) {

    fun setItems(items: List<T>) {
        checkNotNull(items)
        this.items = items
    }

    fun getItemCount(): Int = items.size

    fun bindView(itemView: View, position: Int) {
        onBindView(itemView, items[position])
    }

    abstract fun onBindView(itemView: View, item: T)
}
