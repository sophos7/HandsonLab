package com.example.hands_onlab

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface HttpBinAPI {

    @GET("/get")
    fun getButton(): Deferred<Response<String>>

    @POST("/post")
    fun postButton(@Body body: PostBody): Deferred<Response<String>>

    @PUT("/put")
    fun putButton(@Body body: PostBody): Deferred<Response<String>>

    @DELETE("/delete")
    fun deleteButton(): Deferred<Response<String>>

    @GET("/json")
    fun downloadButton(): Deferred<Response<String>>

    @GET("/status/418")
    fun errorButton(): Deferred<Response<String>>
}

data class PostBody(var name:String = "morpheus", var job:String = "leader")