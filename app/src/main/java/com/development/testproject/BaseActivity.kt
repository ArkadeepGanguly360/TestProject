package com.development.testproject

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.development.testproject.databinding.ActivityBaseBinding
import com.development.testproject.restService.RestInterface
import com.development.testproject.restService.RestModule

@Suppress("DEPRECATION")
open class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding
    private lateinit var progressDialog: Dialog
    private lateinit var restInterface: RestInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_base)
        createRestInterface()
        createProgressDialog()
    }

    fun createRestInterface(): RestInterface {
        restInterface = RestModule.getRetrofitInstance()!!.create(RestInterface::class.java)
        return restInterface
    }

    private fun createProgressDialog() {
        progressDialog = Dialog(this)
        val inflatedView = LayoutInflater.from(this).inflate(R.layout.progressbar_layout, null)
        progressDialog.setContentView(inflatedView)
        progressDialog.setCancelable(false)

        val window = progressDialog.window
        window?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#10000000")))
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        val wlp = window.attributes

        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window.attributes = wlp
    }

    fun showProgressDialog() {
        progressDialog.show()
    }

    fun cancelProgressDialog() {
        progressDialog.cancel()
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun checkInternetConnection(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
}