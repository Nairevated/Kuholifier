package com.example.kuholifier

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(private val recipeList: ArrayList<RecipeData>)
    : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>(){
        var onItemClick : ((RecipeData) -> Unit)? = null

    class RecipeViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.recipeImg)
        val textView: TextView = itemView.findViewById(R.id.recipeName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_data, parent, false)
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.imageView.setImageResource(recipe.recipeImg)
        holder.textView.text = recipe.recipeName

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(recipe)
        }
    }
}