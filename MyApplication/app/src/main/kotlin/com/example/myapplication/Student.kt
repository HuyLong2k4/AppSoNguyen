package com.example.myapplication

import java.io.Serializable

data class Student(
    val mssv: String,
    var hoTen: String,
    var sdt: String,
    var diaChi: String
) : Serializable