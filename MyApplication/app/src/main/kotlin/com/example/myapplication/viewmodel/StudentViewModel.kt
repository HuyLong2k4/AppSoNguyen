package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Student

class StudentViewModel : ViewModel() {

    private val _students = MutableLiveData<MutableList<Student>>(mutableListOf())
    val students: LiveData<MutableList<Student>> = _students

    private val _selectedStudent = MutableLiveData<Student?>()
    val selectedStudent: LiveData<Student?> = _selectedStudent

    init {
        // Dữ liệu mẫu
        _students.value = mutableListOf(
            Student("SV001", "Nguyễn Văn A", "0901234567", "Hà Nội"),
            Student("SV002", "Trần Thị B", "0912345678", "Hải Phòng"),
            Student("SV003", "Lê Văn C", "0923456789", "Đà Nẵng"),
            Student("SV004", "Phạm Thị D", "0934567890", "TP.HCM"),
            Student("SV005", "Hoàng Văn E", "0945678901", "Cần Thơ")
        )
    }

    fun addStudent(student: Student) {
        val currentList = _students.value ?: mutableListOf()
        currentList.add(student)
        _students.value = currentList
    }

    fun updateStudent(mssv: String, hoTen: String, soDienThoai: String, diaChi: String) {
        val currentList = _students.value ?: return
        val student = currentList.find { it.mssv == mssv }
        student?.let {
            it.hoTen = hoTen
            it.soDienThoai = soDienThoai
            it.diaChi = diaChi
            _students.value = currentList
        }
    }

    fun deleteStudent(student: Student) {
        val currentList = _students.value ?: return
        currentList.remove(student)
        _students.value = currentList
    }

    fun selectStudent(student: Student) {
        _selectedStudent.value = student
    }

    fun clearSelection() {
        _selectedStudent.value = null
    }
}
