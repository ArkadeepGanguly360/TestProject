package com.development.testproject.model

import com.google.gson.annotations.SerializedName

data class EmployeeData(
    @SerializedName("id") val id : String,
    @SerializedName("employee_name") val employee_name : String,
    @SerializedName("employee_salary") val employee_salary : String,
    @SerializedName("employee_age") val employee_age : String,
    @SerializedName("profile_image") val profile_image : String
)
