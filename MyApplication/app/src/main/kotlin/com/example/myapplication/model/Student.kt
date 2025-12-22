package com.example.myapplication.model

data class Student(
    val mssv: String,
    var hoTen: String,
    var soDienThoai: String,
    var diaChi: String
) {
    override fun toString(): String {
        return "$mssv - $hoTen"
    }
}