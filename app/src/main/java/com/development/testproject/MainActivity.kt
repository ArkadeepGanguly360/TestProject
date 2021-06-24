package com.development.testproject

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.development.testproject.model.EmployeesData
import com.development.testproject.model.EmployeesResponse
import com.development.testproject.adapter.EmployeesAdapter
import com.development.testproject.databinding.ActivityMainBinding
import com.development.testproject.interfaces.RecyclerViewItemOnClickListener

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val employeeList = ArrayList<EmployeesData>()
    private lateinit var employeesAdapter : EmployeesAdapter
    private var employeeIdList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.setActivities(this,this)

        initAdapter {
            employeesAdapter = it
            binding.adapter = employeesAdapter
        }

        if(checkInternetConnection()) {
            viewModel.getEmployees()
        }
        else {
            showToast("No Internet Connection")
        }
        observeEmployeeList()
    }

    private fun observeEmployeeList(){
        val observer = Observer<EmployeesResponse>{
            if(it!=null) {
                employeeList.addAll(it.data)
                employeesAdapter.notifyDataSetChanged()
            }
        }
        viewModel.observeEmployeeList()?.observe(this,observer)
    }

    private fun initAdapter(listener: (EmployeesAdapter) -> Unit) {
        listener(
            EmployeesAdapter(
                this,
                employeeList,
                object :
                    RecyclerViewItemOnClickListener {
                    override fun onViewClick(idList: ArrayList<String>) {
                        employeeIdList = idList
                        Log.e("MainActivity", "IdList : " + employeeIdList)
                    }
                })
        )
    }
}