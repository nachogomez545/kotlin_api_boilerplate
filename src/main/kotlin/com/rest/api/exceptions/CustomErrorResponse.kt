package com.rest.api.exceptions

data class CustomErrorResponse (val timestamp: Long, val status: Int, val code: String, val message: String, val details: String)