package com.example.hands_onlab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    val service: HttpBinAPI by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://httpbin.org/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        retrofit.create(HttpBinAPI::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // Button methods
        getButton.setOnClickListener { launch { getButton() } }

        postButton.setOnClickListener { launch { postButton() } }

        putButton.setOnClickListener { launch { putButton() } }

        deleteButton.setOnClickListener { launch { deleteButton() } }

        downloadButton.setOnClickListener { launch { downloadButton() } }

    }

    suspend fun getButton(){
        val response = service.getButton().await()
        Toast.makeText(this, "getButton clicked", Toast.LENGTH_SHORT).show()
    }

    suspend fun postButton(){
        val response = service.postButton(body = PostBody()).await()
        Toast.makeText(this, "getButton clicked", Toast.LENGTH_SHORT).show()
    }

    suspend fun putButton(){
        service.putButton(body = PostBody())
    }

    suspend fun deleteButton(){
        service.deleteButton()
    }

    suspend fun downloadButton(){
        service.downloadButton()
    }
}
