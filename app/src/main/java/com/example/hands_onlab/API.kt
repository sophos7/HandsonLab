package com.example.hands_onlab

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface HttpBinAPI {

    @GET("/get")
    fun getButton(): Deferred<Response<ResponseBody>>

    @POST("/post")
    fun postButton(@Body body: PostBody): Deferred<Response<ResponseBody>>

    @PUT("/put")
    fun putButton(@Body body: PostBody): Deferred<Response<ResponseBody>>

    @DELETE("/delete")
    fun deleteButton(): Deferred<Response<ResponseBody>>

    @GET("/stream/1")
    fun downloadButton(): Deferred<Response<ResponseBody>>


}

data class PostBody(val name:String = "morpheus", val job:String = "leader")