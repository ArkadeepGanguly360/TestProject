package com.development.testproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.development.testproject.R
import com.development.testproject.databinding.EmployeeListItemBinding
import com.development.testproject.interfaces.RecyclerViewItemOnClickListener
import com.development.testproject.model.EmployeeData

class EmployeesAdapter(
    private val context: Context,
    private val list: ArrayList<EmployeeData>,
    private val listener: RecyclerViewItemOnClickListener
) :
    RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {
    private val employeeIdList = ArrayList<String>()

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return list[position].hashCode().toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val rowBinding: EmployeeListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.employee_list_item,
            parent,
            false
        )
        return ViewHolder(rowBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]
        holder.rowBinding.name = item.employee_name
        holder.rowBinding.salary = item.employee_salary

        holder.rowBinding.cbEmployee.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                employeeIdList.add(list[position].id);
                updateEmployeeIdList()
            } else {
                employeeIdList.remove(list[position].id);
                updateEmployeeIdList()
            }
        }
    }

    private fun updateEmployeeIdList() {
        listener.onViewClick(employeeIdList)
    }

    inner class ViewHolder(val rowBinding: EmployeeListItemBinding) :
        RecyclerView.ViewHolder(rowBinding.root)
}
