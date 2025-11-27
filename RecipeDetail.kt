package com.example.kuholifier

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RecipeDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.recipe_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left,systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recipe = intent.getParcelableExtra<RecipeData>("recipe")
        if (recipe !=null){
            val imageView : ImageView = findViewById(R.id.recipedetail1)
            val textView1: TextView = findViewById(R.id.recipedetail2)
            val textView2: TextView = findViewById(R.id.recipedetail3)
            val textView3: TextView = findViewById(R.id.recipedetail4)
            val textView4: TextView = findViewById(R.id.recipedetail5)

            imageView.setImageResource(recipe.recipeImg)
            textView1.text = recipe.recipeName
            textView2.text = recipe.recipeIngredient
            textView3.text = recipe.recipePrep
            textView4.text = recipe.recipeCook
        }
    }
}