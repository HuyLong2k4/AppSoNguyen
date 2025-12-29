package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.database.StudentDatabase
import com.example.myapplication.model.Student
import com.example.myapplication.repository.StudentRepository
import kotlinx.coroutines.launch

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StudentRepository
    val students: LiveData<List<Student>>

    private val _selectedStudent = MutableLiveData<Student?>()
    val selectedStudent: LiveData<Student?> = _selectedStudent

    private val _operationStatus = MutableLiveData<String>()
    val operationStatus: LiveData<String> = _operationStatus

    init {
        val studentDao = StudentDatabase.getDatabase(application).studentDao()
        repository = StudentRepository(studentDao)
        students = repository.allStudents
    }

    fun addStudent(student: Student) = viewModelScope.launch {
        try {
            repository.insert(student)
            _operationStatus.value = "Thêm sinh viên thành công!"
        } catch (e: Exception) {
            _operationStatus.value = "Lỗi: ${e.message}"
        }
    }

    fun updateStudent(student: Student) = viewModelScope.launch {
        try {
            repository.update(student)
            _operationStatus.value = "Cập nhật thông tin thành công!"
        } catch (e: Exception) {
            _operationStatus.value = "Lỗi: ${e.message}"
        }
    }

    fun deleteStudent(student: Student) = viewModelScope.launch {
        try {
            repository.delete(student)
            _operationStatus.value = "Xóa sinh viên thành công!"
        } catch (e: Exception) {
            _operationStatus.value = "Lỗi: ${e.message}"
        }
    }

    fun selectStudent(student: Student) {
        _selectedStudent.value = student
    }

    fun clearSelection() {
        _selectedStudent.value = null
    }

    fun clearStatus() {
        _operationStatus.value = ""
    }

    suspend fun checkStudentExists(mssv: String): Boolean {
        return repository.getStudentByMssv(mssv) != null
    }
}
