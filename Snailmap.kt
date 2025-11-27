package com.example.kuholifier

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class Snailmap : AppCompatActivity() {
    private lateinit var Dinalupihanbtn: Button
    private lateinit var Hermosabtn: Button
    private lateinit var Oranibtn: Button
    private lateinit var Samalbtn: Button
    private lateinit var Abucaybtn: Button
    private lateinit var Balangabtn: Button
    private lateinit var Pilarbtn: Button
    private lateinit var Orionbtn: Button
    private lateinit var Limaybtn: Button
    private lateinit var Marivelesbtn: Button
    private lateinit var Bagacbtn: Button
    private lateinit var Morongbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_map)

        Dinalupihanbtn = findViewById(R.id.Dinalupihanbtn)
        Hermosabtn = findViewById(R.id.Hermosabtn)
        Oranibtn = findViewById(R.id.Oranibtn)
        Samalbtn = findViewById(R.id.Samalbtn)
        Abucaybtn = findViewById(R.id.Abucaybtn)
        Balangabtn = findViewById(R.id.Balangabtn)
        Pilarbtn = findViewById(R.id.Pilarbtn)
        Orionbtn = findViewById(R.id.Orionbtn)
        Limaybtn = findViewById(R.id.Limaybtn)
        Marivelesbtn = findViewById(R.id.Marivelesbtn)
        Bagacbtn = findViewById(R.id.Bagacbtn)
        Morongbtn = findViewById(R.id.Morongbtn)

        val buttonIds = listOf(
            R.id.Dinalupihanbtn,
            R.id.Hermosabtn,
            R.id.Oranibtn,
            R.id.Samalbtn,
            R.id.Abucaybtn,
            R.id.Balangabtn,
            R.id.Pilarbtn,
            R.id.Orionbtn,
            R.id.Limaybtn,
            R.id.Marivelesbtn,
            R.id.Bagacbtn,
            R.id.Morongbtn
        )
        for (buttonId in buttonIds) {
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener{
                val mapData = MapDetail.locationData[buttonId]
                mapData?.let {data ->
                   val message = """
                       Name: ${data.name}
                       Biluluko Density = ${data.snail1}
                       Bayuko Density = ${data.snail2}
                   """.trimIndent()

                    AlertDialog.Builder(this)
                        .setTitle("Location Info")
                        .setMessage(message)
                        .setPositiveButton("OK",null)
                        .show()
                }
            }
        }
    }
}