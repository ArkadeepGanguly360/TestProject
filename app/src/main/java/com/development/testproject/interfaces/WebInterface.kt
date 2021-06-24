package com.development.testproject.interfaces

interface WebInterface {
    fun <E> resultSuccess(t: E, method_name: String?)
    fun failureSuccess(s: String?)
}