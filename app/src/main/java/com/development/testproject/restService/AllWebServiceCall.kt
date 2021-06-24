package com.development.testproject.restService

import com.development.testproject.interfaces.WebInterface
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class AllWebServiceCall() {

    fun webServiceCall(
        responseBody: Call<ResponseBody>?,
        webInterface: WebInterface,
        method_name: String
    ) {
        responseBody!!.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                try {
                    if (response.code() === 200 ) {
                        responce = response.body()!!.string()
                        val jsonObject = JSONObject(responce)
                        if (jsonObject.optString("status") == "success") {
                            webInterface.resultSuccess(responce, method_name)
                        } else {
                            if (jsonObject.has("message")) {
                                webInterface.failureSuccess(jsonObject.optString("message"))
                            }
                        }
                    } else if (response.code() === 201) {
                        val jsonObject: JSONObject
                        try {
                            responce = response.body()!!.string()
                            jsonObject = JSONObject(responce)
                            if (jsonObject.getString("status") == "success")
                                webInterface.resultSuccess(
                                responce,
                                method_name
                            ) else webInterface.failureSuccess(responce)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            webInterface.failureSuccess(responce)
                        }
                    } else {
                        if (response.errorBody() != null) {
                            try {
                                responce = response.errorBody()!!.string()
                                val jsonObject = JSONObject(responce)
                                if (jsonObject.has("message")) webInterface.failureSuccess(
                                    jsonObject.optString("message")
                                )
                            } catch (e: JSONException) {
                                e.printStackTrace()
                                webInterface.failureSuccess(responce)
                            }
                        } else {
                            try {
                                responce = response.body()!!.string()
                                val jsonObject = JSONObject(responce)
                                if (jsonObject.has("message")) webInterface.failureSuccess(
                                    jsonObject.optString("message")
                                )
                            } catch (e: JSONException) {
                                e.printStackTrace()
                                webInterface.failureSuccess(responce)
                            }
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }

            override fun onFailure(
                call: Call<ResponseBody>,
                t: Throwable
            ) {
                webInterface.failureSuccess(t.message)
            }
        })
    }

    companion object {
        private var responce = ""
    }
}