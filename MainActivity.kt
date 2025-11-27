package com.example.kuholifier

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import java.io.IOException
import android.Manifest
import android.content.res.AssetManager
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var captureBtn: Button
    private lateinit var predictBtn: Button
    private lateinit var selectBtn: Button
    private lateinit var mainResult: TextView
    private lateinit var bitmap: Bitmap
    private lateinit var imageView: ImageView
    private var interpreter: Interpreter? = null
    private lateinit var labels: List<String>
    private val inputImageWidth = 256
    private val inputImageHeight = 256
    private val outputClassesCount = 2
    private lateinit var username: String
    private lateinit var snailBtn: Button
    private lateinit var snailMap: Button
    private lateinit var recipeBtn: Button
    private lateinit var fusedLocation: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            loadModelinBackground()
        }
        getPermission()
        getLocationPermission()

        fusedLocation = LocationServices.getFusedLocationProviderClient(this)

        username = intent.getStringExtra("USERNAME") ?: run {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        selectBtn = findViewById(R.id.selectBtn)
        captureBtn = findViewById(R.id.captureBtn)
        predictBtn = findViewById(R.id.predictBtn)
        mainResult = findViewById(R.id.mainResult)
        imageView = findViewById(R.id.imageView)
        snailBtn = findViewById(R.id.snailBtn)
        snailMap = findViewById(R.id.snailMap)
        recipeBtn = findViewById(R.id.recipeBtn)


        selectBtn.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_GET_CONTENT
                type = "image/*"
            }
            startActivityForResult(intent,10)
        }
        snailBtn.setOnClickListener {
            val intent = Intent(this,SnailSet::class.java)
            startActivity(intent)
        }
        snailMap.setOnClickListener {
            val intent = Intent (this,Snailmap::class.java)
            startActivity(intent)
        }
        recipeBtn.setOnClickListener {
            val intent = Intent(this,RecipeSet::class.java)
            startActivity(intent)
        }
        captureBtn.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 12)
        }
        predictBtn.setOnClickListener {
            runInference()
        }

        val assetManager = assets
        val model = loadModelFile(assetManager, "snailVGG2.tflite")
        val tfliteOptions = Interpreter.Options()
        interpreter = Interpreter(model,tfliteOptions)
        labels = loadLabels(assetManager, "snail_types.txt")
    }

    private fun getPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 11)
        }
    }
    private fun getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),100)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode==11){
            if (grantResults.isNotEmpty() && grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                this.getPermission()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onActivityResult (requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uri)
                    imageView.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }else if (requestCode == 12 && resultCode == Activity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as? Bitmap
            bitmap?.let {
                imageView.setImageBitmap(it)
                this.bitmap = it
            }
        }
    }
    private suspend fun loadModelinBackground()= withContext(Dispatchers.IO) {
        try {
            val model = loadModelFile(assets,"snailVGG2.tflite")
            val tfliteOptions = Interpreter.Options()
            interpreter = Interpreter(model, tfliteOptions)
            Log.d("TFlite","Model loaded successfully")
        } catch (e:Exception) {
            Log.e("TFlite","Failed to load model: ${e.message}")
        }
    }
    private fun loadModelFile(assetManager: AssetManager, modelFilename: String): ByteBuffer {
        val fileDescriptor = assetManager.openFd(modelFilename)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declaredLength)

    }

    private fun loadLabels(assetManager: AssetManager, labelFilename: String): List<String> {
        return assetManager.open(labelFilename).bufferedReader().use { it.readLines() }
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap):ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * inputImageWidth * inputImageHeight * 3)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(inputImageWidth * inputImageHeight)
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        var pixel = 0
        for (i in 0 until inputImageWidth) {
            for (j in 0 until inputImageHeight) {
                val value = intValues[pixel++]
                val red = android.graphics.Color.red(value)
                val green = android.graphics.Color.green(value)
                val blue = android.graphics.Color.blue(value)
                byteBuffer.putFloat(red / 255.0f)
                byteBuffer.putFloat(green / 255.0f)
                byteBuffer.putFloat(blue / 255.0f)

            }
        }
        return byteBuffer
    }
    private fun runInference(){
        if(!::bitmap.isInitialized) {
            mainResult.text = "Please select an image first"
            return
        }
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, inputImageWidth, inputImageHeight, true)
        val inputData = convertBitmapToByteBuffer(resizedBitmap)
        val output = Array(1) {FloatArray(outputClassesCount)}
        interpreter?.run(inputData,output)

        val result = output[0]
        val maxIndex = result.indices.maxByOrNull { result[it] }?: 0
        val predictedLabel = labels[maxIndex]
        val confidence = result[maxIndex]

        mainResult.text = if (confidence < 0.98) {
            "Prediction: Unidentifiable"
        } else {
            getCurrentLocation{lat, lon ->
                uploadPredictiontoSupabase(predictedLabel, lat, lon)
            }
            "Prediction: $predictedLabel\nConfidence: ${"%.2f".format(confidence * 100)}%"
        }
    }
    private fun getCurrentLocation(onResult: (Double,Double) -> Unit) {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show()
            return
        }
        fusedLocation.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                onResult(location.latitude, location.longitude)
            }
            else {
                Toast.makeText(this,"Location not found", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun uploadPredictiontoSupabase(snail: String, lat: Double, lon: Double) {
        lifecycleScope.launch {
            try {
                SupabaseClient.supabase.postgrest["predictions"].insert(
                    mapOf(
                        "username" to username,
                        "snail_type" to snail,
                        "latitude" to lat,
                        "longitude" to lon,
                        "timestamp" to System.currentTimeMillis()
                    )
                )
                Log.d("Supabase", "Upload Successful")
            } catch (e:Exception) {
                Log.e("Supabase", "Upload Failed ${e.message}")
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        interpreter?.close()
    }
}