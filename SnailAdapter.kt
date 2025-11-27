package com.example.kuholifier

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SnailAdapter(private val snailList: ArrayList<SnailData>)
    : RecyclerView.Adapter<SnailAdapter.SnailViewHolder>(){
        var onItemClick : ((SnailData) -> Unit)? = null

    class SnailViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.snailImg)
        val textView: TextView = itemView.findViewById(R.id.snailInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SnailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.snail_data, parent, false)
        return SnailViewHolder(view)
    }
    override fun getItemCount(): Int {
        return snailList.size
    }

    override fun onBindViewHolder(holder: SnailViewHolder, position: Int) {
        val snail = snailList[position]
        holder.imageView.setImageResource(snail.image)
        holder.textView.text = snail.name

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(snail)

        }
    }
}