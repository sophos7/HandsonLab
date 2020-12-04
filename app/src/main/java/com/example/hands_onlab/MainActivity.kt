package com.example.hands_onlab

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.example.hands_onlab.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var binding: ActivityMainBinding
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val service: HttpBinAPI by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://httpbin.org/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(HttpBinAPI::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUi()
    }

    private fun setUpUi() {
        //set scrolling
        binding.responseText.movementMethod = ScrollingMovementMethod()
        // Button methods
        binding.getButton.setOnClickListener { launch { getButton() } }
        binding.postButton.setOnClickListener { launch { postButton() } }
        binding.putButton.setOnClickListener { launch { putButton() } }
        binding.deleteButton.setOnClickListener { launch { deleteButton() } }
        binding.downloadButton.setOnClickListener { launch { downloadButton() } }
        binding.errorButton.setOnClickListener { launch { errorButton() } }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private suspend fun getButton() {
        val response = service.getButton().await()
        binding.responseText.text = "Get Response : \n${response.body()}"
        Toast.makeText(this, "Get Button clicked", Toast.LENGTH_SHORT).show()
    }

    private suspend fun postButton() {
        val response = service.postButton(body = PostBody()).await()
        binding.responseText.text = "Post Response :\n${response.body()}"
        Toast.makeText(this, "Post Button clicked", Toast.LENGTH_SHORT).show()
    }

    private suspend fun putButton() {
        val response = service.putButton(body = PostBody(name = "larry", job = "bobo")).await()
        binding.responseText.text = "Put Response :\n${response.body()}"
        Toast.makeText(this, "Put Button clicked", Toast.LENGTH_SHORT).show()
    }

    private suspend fun deleteButton() {
        val response = service.deleteButton().await()
        binding.responseText.text = "Delete Response :\n${response.body()}"
        Toast.makeText(this, "Delete Button clicked", Toast.LENGTH_SHORT).show()
    }

    private suspend fun downloadButton() {
        val response = service.downloadButton().await()
        binding.responseText.text = "Download Response :\n${response.body()}"
        Toast.makeText(this, "Download Button clicked", Toast.LENGTH_SHORT).show()
    }

    private suspend fun errorButton() {
        val response = service.errorButton().await()
        binding.responseText.text =
            "Error Response : \n${response.errorBody()?.string() ?: "there is no errorBody string"}"
        Toast.makeText(this, "Error Button clicked", Toast.LENGTH_SHORT).show()

        val num1 = 0
        val num2 = 42

        try {
            val result = num2 / num1

        } catch (e: Exception) {
            val attributesMap = mapOf<String, Any>(Pair("num1", num1), Pair("num2", num2))
            Toast.makeText(this, "num1: " + num1 + " num2: " + num2, Toast.LENGTH_SHORT).show()
        }
    }
}
