package com.development.testproject

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.development.testproject.adapter.EmployeesAdapter
import com.development.testproject.databinding.ActivityMainBinding
import com.development.testproject.interfaces.RecyclerViewItemOnClickListener
import com.development.testproject.model.EmployeeData
import com.development.testproject.model.EmployeeResponse

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val employeeList = ArrayList<EmployeeData>()
    private lateinit var employeesAdapter : EmployeesAdapter
    private var employeeIdList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.setActivities(this, this)

        initAdapter {
            employeesAdapter = it
            binding.adapter = employeesAdapter
        }

        getEmployees()
        observeEmployeeList()

    }

    private fun getEmployees() {
        if(checkInternetConnection()) {
            viewModel.getEmployees()
        }
        else {
            showToast("No Internet Connection")
        }
    }

    private fun observeEmployeeList(){
        val observer = Observer<EmployeeResponse>{
            if(it!=null) {
                employeeList.addAll(it.data)
                employeesAdapter.notifyDataSetChanged()
            }
        }
        viewModel.observeEmployeeList().observe(this,observer)
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