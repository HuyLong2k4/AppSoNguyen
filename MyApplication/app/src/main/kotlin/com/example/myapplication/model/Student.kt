package com.example.myapplication.model
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey
    val mssv: String,
    var hoTen: String,
    var soDienThoai: String,
    var diaChi: String
) {
    override fun toString(): String {
        return "$mssv - $hoTen"
    }
}