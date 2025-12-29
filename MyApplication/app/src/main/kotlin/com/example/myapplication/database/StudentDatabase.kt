package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.model.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Student::class], version = 1, exportSchema = false)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getDatabase(context: Context): StudentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "student_database"
                )
                    .addCallback(StudentDatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class StudentDatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.studentDao())
                }
            }
        }

        suspend fun populateDatabase(studentDao: StudentDao) {
            // Xóa tất cả dữ liệu
            studentDao.deleteAllStudents()

            // Thêm dữ liệu mẫu
            val sampleStudents = listOf(
                Student("SV001", "Nguyễn Văn A", "0901234567", "Hà Nội"),
                Student("SV002", "Trần Thị B", "0912345678", "Hải Phòng"),
                Student("SV003", "Lê Văn C", "0923456789", "Đà Nẵng"),
                Student("SV004", "Phạm Thị D", "0934567890", "TP.HCM"),
                Student("SV005", "Hoàng Văn E", "0945678901", "Cần Thơ")
            )

            sampleStudents.forEach { student ->
                studentDao.insertStudent(student)
            }
        }
    }
}
