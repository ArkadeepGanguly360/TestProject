package com.development.testproject.model

import com.google.gson.annotations.SerializedName

data class EmployeesResponse(
    @SerializedName("status") val status : String,
    @SerializedName("data") val data : List<EmployeesData>,
    @SerializedName("message") val message : String
)
