package com.pdog18.layoutmanager

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class DAAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    private var lastOpenPosition = -1

    private val entities = arrayOf(
        DAEntity(R.mipmap.ic_launcher_round, "回款偏差", +2.4f, 0.0, 90000000.0),
        DAEntity(R.mipmap.ic_launcher, "回款时间偏差", +11.8f, 2000.0, 9999.0),
        DAEntity(R.mipmap.ic_launcher_round, "费用偏差", -11.1f, 1000.0, 500.0),
        DAEntity(R.mipmap.ic_launcher, "抵押物估值偏差", -57.9f, 500.0, 5000.0)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder = object : androidx.recyclerview.widget.RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.da_adapter_item, parent, false)) {}


    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {

        val entity = entities[position]

        holder.itemView.findViewById<ImageView>(R.id.img_label).setImageResource(entity.icon)
        holder.itemView.findViewById<TextView>(R.id.tv_title).text = entity.title
        holder.itemView.findViewById<TextView>(R.id.tv_progress).text = entity.progress.toString()

        holder.itemView.findViewById<TextView>(R.id.tv_forecasts).text = entity.forecasts.toString()
        holder.itemView.findViewById<TextView>(R.id.tv_actual).text = entity.actual.toString()

        val tv_forecasts_left_text = holder.itemView.findViewById<TextView>(R.id.tv_forecasts_left_text)
        val tv_actual_left_text = holder.itemView.findViewById<TextView>(R.id.tv_actual_left_text)

        val forecasts = holder.itemView.findViewById<ProgressView>(R.id.pv_forecasts)
        val actual = holder.itemView.findViewById<ProgressView>(R.id.pv_actual)


        /**
         *
         * 回款/回款时间/抵押物估值的偏差比，为正时，显示为蓝色；为负时，显示为红色。
         *        支出偏差比，为正时，显示为红色；为负时，显示为蓝色。
         * */
        if (entity.actual >= entity.forecasts) {    //蓝色
            val pvColor = Color.parseColor("#6EA5FF")
            val textColor = Color.parseColor("#BAD4FF")
            tv_forecasts_left_text.setTextColor(textColor)
            tv_actual_left_text.setTextColor(textColor)

            actual.update(pvColor, 1f)
            forecasts.update(pvColor, (entity.forecasts / entity.actual).toFloat())
        } else {  //红色
            val pvColor = Color.parseColor("#EB6764")
            val textColor = Color.parseColor("#FFB1B1")
            tv_forecasts_left_text.setTextColor(textColor)
            tv_actual_left_text.setTextColor(textColor)

            forecasts.update(pvColor, 1f)
            actual.update(pvColor, (entity.actual / entity.forecasts).toFloat())
        }

        val view = holder.itemView.findViewById<View>(R.id.cl_bottom)

        if (lastOpenPosition == position) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }


        holder.itemView.setOnClickListener {
            val oldOpenPosition = lastOpenPosition

            lastOpenPosition = if (lastOpenPosition == position) -1 else position


            if (oldOpenPosition != -1) {
                notifyItemChanged(oldOpenPosition)
            }

            if (lastOpenPosition != -1) {
                notifyItemChanged(lastOpenPosition)
            }
        }
    }

    override fun getItemCount() = entities.size
}
