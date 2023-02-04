package com.example.data.remote.dto

data class Current(
    val condition: Condition?=null,
    val is_day: Int?=null,
    val temp_c: Double?=null,
    val temp_f: Double?=null
)