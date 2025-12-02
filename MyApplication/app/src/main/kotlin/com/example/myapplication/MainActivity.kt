package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvSinhVien: RecyclerView
    private var studentList = mutableListOf<Student>()
    private lateinit var adapter: StudentAdapter

    // Launcher để hứng kết quả khi THÊM sinh viên
    private val addStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val newStudent = result.data?.getSerializableExtra("NEW_STUDENT") as? Student
            if (newStudent != null) {
                studentList.add(newStudent)
                adapter.notifyItemInserted(studentList.size - 1)
            }
        }
    }

    // Launcher để hứng kết quả khi SỬA sinh viên
    private val updateStudentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val updatedStudent = result.data?.getSerializableExtra("UPDATED_STUDENT") as? Student
            val position = result.data?.getIntExtra("POSITION", -1) ?: -1

            if (updatedStudent != null && position != -1) {
                studentList[position] = updatedStudent
                adapter.notifyItemChanged(position)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvSinhVien = findViewById(R.id.rv_sinh_vien)

        // Khởi tạo Adapter
        // Lưu ý: Adapter vẫn dùng layout item_student cũ (chỉ hiện Tên và MSSV)
        adapter = StudentAdapter(
            studentList,
            onItemClick = { position ->
                // Khi click vào item, mở Detail Activity
                val intent = Intent(this, StudentDetailActivity::class.java)
                intent.putExtra("STUDENT_DATA", studentList[position])
                intent.putExtra("POSITION", position)
                updateStudentLauncher.launch(intent)
            },
            onDeleteClick = { position ->
                studentList.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
        )

        rvSinhVien.layoutManager = LinearLayoutManager(this)
        rvSinhVien.adapter = adapter
    }

    // Tạo Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Xử lý sự kiện click Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this, AddStudentActivity::class.java)
                addStudentLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}