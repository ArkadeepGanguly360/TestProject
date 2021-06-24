package com.development.testproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.development.testproject.interfaces.WebInterface
import com.development.testproject.model.EmployeeResponse
import com.development.testproject.restService.AllWebServiceCall
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

class MainViewModel : ViewModel(), WebInterface {
    private lateinit var baseActivity: BaseActivity
    private lateinit var mainActivity: MainActivity
    private var employeeMutableLiveData = MutableLiveData<EmployeeResponse>()

    fun setActivities(baseActivity: BaseActivity, mainActivity: MainActivity) {
        this.baseActivity = baseActivity
        this.mainActivity = mainActivity
    }

    fun observeEmployeeList(): MutableLiveData<EmployeeResponse> {
        return employeeMutableLiveData;
    }

    fun getEmployees() {
        baseActivity.showProgressDialog()
        val query = baseActivity.createRestInterface().getEmployees()
        AllWebServiceCall().webServiceCall(query, this, "getEmployees")
    }

    override fun <E> resultSuccess(t: E, method_name: String?) {
        try {
            val response = Gson().fromJson(t.toString(), EmployeeResponse::class.java)
            employeeMutableLiveData.postValue(response)
        } catch (e: JSONException) {
            e.printStackTrace()
        } finally {
            baseActivity.cancelProgressDialog()
        }
    }

    override fun failureSuccess(s: String?) {
        try {
            val jsonObject = JSONObject(s!!)
            if (jsonObject.has("message"))
                baseActivity.showToast(jsonObject.optString("message"))
        } catch (e: JSONException) {
            baseActivity.showToast(s!!)
        } finally {
            baseActivity.cancelProgressDialog()
        }
    }
}