package com.development.testproject.restService


import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.*
import java.util.HashMap

interface RestInterface {

    @GET("employees")
    fun getEmployees(): Call<ResponseBody>
}