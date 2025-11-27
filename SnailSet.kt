package com.example.kuholifier

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SnailSet : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var snailList: ArrayList<SnailData>
    private lateinit var snailAdapter: SnailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.snail_recyclerview)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        snailList = ArrayList()

        snailList.add(SnailData(R.drawable.lissachatina,
            "Name: Biluluko",
            "Scientific name: Lissachatina Fulica",
            "Toxicology: cannot be eaten raw or undercooked "))

        snailList.add(SnailData(R.drawable.ryssota,
            "Name: Bayuku",
            "Scientific Name: Ryssota ovum",
            "Toxicology: cannot be eaten raw or undercooked"))

        snailAdapter = SnailAdapter(snailList)
        recyclerView.adapter = snailAdapter
        snailAdapter.onItemClick = {
            val intent = Intent(this,SnailDetail::class.java)
            intent.putExtra("snail",it)
            startActivity(intent)
        }
    }
}