package com.example.kuholifier

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SnailDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.snail_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val snail = intent.getParcelableExtra<SnailData>("snail")
        if (snail !=null){
            val textView : TextView = findViewById(R.id.snaildetail2)
            val imageView : ImageView = findViewById(R.id.snaildetail1)
            val textView2 : TextView = findViewById(R.id.snaildetail3)
            val textView3 : TextView = findViewById(R.id.snaildetail4)

            textView.text = snail.name
            imageView.setImageResource(snail.image)
            textView2.text = snail.scname
            textView3.text = snail.toxicology
        }
    }
}