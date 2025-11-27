package com.example.kuholifier

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecipeSet : AppCompatActivity() {
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recipeList: ArrayList<RecipeData>
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.recipe_recyclerview)

        recyclerView2 = findViewById(R.id.recyclerView2)
        recyclerView2.setHasFixedSize(true)
        recyclerView2.layoutManager = LinearLayoutManager(this)

        recipeList = ArrayList()
        recipeList.add(RecipeData(R.drawable.adobo_recipe,
            "Adobong Biluluko",
            """
            Ingredients:
            1. Luya 
            2. Bawang
            3. Sibuyas
            4. Toyo
            5. Siling Labuyo
            6. Mantika
            7. Asin
            8. Seasoning
            9. Suka
            """.trimIndent(),
            """
            Preparation:
            
            1. Pakuluan ang Biluluko for 15-30 minutes para matanggal ang parasite
            
            2. ibabad sa tubig para lumamig
            
            3. tanggalin ang Biluluko sa kanyang shell
            
            4. tanggalin ang dumi sa loob ng Biluluko
            
            5. Lamasin ang Biluluko sa tubig para matanggal ang bula nito
            
            6. Lamasin ulit sa asin
            """.trimIndent(),
            """
            Cooking:
            
            1. Magpainit ng mantika
            
            2. Gisahin ang bawang at sibuyas
            
            3. Iluto ang biluluko
            
            4. Ilagay ang siling labuyo
            
            5. Ilagay ang toyo
            
            6. Ilagay ang asin
            
            7. Ilagay ang seasoning
            
            8. Ilagay ang suka
            
            9. Iluto hanggang matuyo ang sabaw
            
            """.trimIndent()))
        recipeList.add(RecipeData(R.drawable.ginataan_recipe,
            "Ginataang Bayuko",
            """
            Ingredients:
            
            1. Malunggay
            2. Kakang gata
            3. Bawang
            4. Luya
            5. Siling labuyo
            6. Paminta
            7. Asin
            8. Laurel
            9. Seasoning
            10. Suka
            """.trimIndent(),
            """
            Preperation:
            
            1. Pakuluan ang Bayuko for 15-30 minutes para matanggal ang parasite
            
            2. ibabad sa tubig para lumamig
            
            3. tanggalin ang Bayuko sa kanyang shell
            
            4. tanggalin ang dumi sa loob ng Bayuko
            
            5. Lamasin ang Bayuko sa tubig para matanggal ang bula nito
            
            6. Lamasin ulit sa asin
            """.trimIndent(),
            """
            Coooking:
            1. Gisahin ang bawang at luya
            
            2. Ilagay ang Bayuko
            
            3. Ilagay ang siling labuyo
            
            4. Maglagay ng paminta
            
            5. Ilagay ang asin
            
            6. Ilagay ang laurel
            
            7. Ilagay ang seasoning
            
            8. Ilagay ang suka
            
            9. Ilagay ang kakang gata
            
            10. Ilagay ang malunggay
            
            11. Iluto hanggang matuyo ang sabaw
            
            """.trimIndent()))

        recipeAdapter = RecipeAdapter(recipeList)
        recyclerView2.adapter = recipeAdapter
        recipeAdapter.onItemClick = {
            val intent = Intent (this,RecipeDetail::class.java)
            intent.putExtra("recipe",it)
            startActivity(intent)
        }
    }
}