package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.database.StudentDao
import com.example.myapplication.model.Student

class StudentRepository(private val studentDao: StudentDao) {

    val allStudents: LiveData<List<Student>> = studentDao.getAllStudents()

    suspend fun insert(student: Student) {
        studentDao.insertStudent(student)
    }

    suspend fun update(student: Student) {
        studentDao.updateStudent(student)
    }

    suspend fun delete(student: Student) {
        studentDao.deleteStudent(student)
    }

    suspend fun getStudentByMssv(mssv: String): Student? {
        return studentDao.getStudentByMssv(mssv)
    }

    suspend fun deleteAll() {
        studentDao.deleteAllStudents()
    }

    suspend fun getCount(): Int {
        return studentDao.getStudentCount()
    }
}