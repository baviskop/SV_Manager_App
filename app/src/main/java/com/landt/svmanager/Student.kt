package com.landt.svmanager

import java.io.Serializable

data class Student(
    val maSv: String,
    val name: String,
    val gender: String,
    val age: Int
) : Serializable