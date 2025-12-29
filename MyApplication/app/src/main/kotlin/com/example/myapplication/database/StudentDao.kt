package com.example.myapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.Student

@Dao
interface StudentDao {

    @Query("SELECT * FROM students ORDER BY mssv ASC")
    fun getAllStudents(): LiveData<List<Student>>

    @Query("SELECT * FROM students WHERE mssv = :mssv")
    suspend fun getStudentByMssv(mssv: String): Student?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("DELETE FROM students")
    suspend fun deleteAllStudents()

    @Query("SELECT COUNT(*) FROM students")
    suspend fun getStudentCount(): Int
}
