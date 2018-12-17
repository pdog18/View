package com.pdog18.layoutmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import timber.log.Timber

class SimpleAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<SimpleAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.textview, parent, false)

        val holder = VH(itemView)
        holder.itemView.setOnClickListener {
            Timber.d("holder.oldPosition = ${holder.oldPosition}")
            Timber.d("holder.adapterPosition    = ${holder.adapterPosition}")
            Timber.d("holder.layoutPosition  = ${holder.layoutPosition}")
            Timber.d("holder.position            = ${holder.position}")
            bindView(holder)
        }

        return holder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        bindView(holder)
    }

    private fun bindView(holder: VH) {
        with(holder.itemView as TextView) {
            this.text = """
                    "holder.adapterPosition = ${holder.adapterPosition})")
                    "holder.layoutPosition = ${holder.layoutPosition}")
                    "holder.oldPosition = ${holder.oldPosition}")
                    "holder.position = ${holder.position}")
                """.trimIndent()
        }
    }

    override fun getItemCount(): Int {
        return 200
    }

    class VH(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
}
