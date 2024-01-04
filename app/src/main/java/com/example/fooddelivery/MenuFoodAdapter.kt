package com.example.fooddelivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuFoodAdapter(
    private val imgs: List<Int>,
    private val names: List<String>,
    private val prices: List<Int>
): RecyclerView.Adapter<MenuFoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.img_food)
        val name: TextView = itemView.findViewById(R.id.tv_name_food)
        val price: TextView = itemView.findViewById(R.id.tv_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_menu, parent, false)
        return FoodViewHolder(itemView)
    }

    override fun getItemCount(): Int = names.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.img.setImageResource(imgs[position])
        holder.name.text = names[position]
        holder.price.setText("\$${prices[position].toString()}")
    }

}