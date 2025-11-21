package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var etMSSV: EditText
    private lateinit var etHoTen: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var rvSinhVien: RecyclerView

    private var studentList = mutableListOf<Student>()
    private lateinit var adapter: StudentAdapter
    private var selectedPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        setupListeners()
    }

    private fun initViews() {
        etMSSV = findViewById(R.id.et_mssv)
        etHoTen = findViewById(R.id.et_ho_ten)
        btnAdd = findViewById(R.id.btn_add)
        btnUpdate = findViewById(R.id.btn_update)
        rvSinhVien = findViewById(R.id.rv_sinh_vien)
    }

    private fun setupRecyclerView() {
        adapter = StudentAdapter(studentList) { position ->
            onStudentSelected(position)
        }
        rvSinhVien.layoutManager = LinearLayoutManager(this)
        rvSinhVien.adapter = adapter
    }

    private fun setupListeners() {
        btnAdd.setOnClickListener { addStudent() }
        btnUpdate.setOnClickListener { updateStudent() }
        btnUpdate.isEnabled = false
    }

    private fun addStudent() {
        val mssv = etMSSV.text.toString().trim()
        val hoTen = etHoTen.text.toString().trim()

        if (mssv.isEmpty() || hoTen.isEmpty()) {
            android.widget.Toast.makeText(this, "Vui lòng điền đầy đủ thông tin",
                android.widget.Toast.LENGTH_SHORT).show()
            return
        }

        // Kiểm tra MSSV đã tồn tại
        if (studentList.any { it.mssv == mssv }) {
            android.widget.Toast.makeText(this, "MSSV đã tồn tại",
                android.widget.Toast.LENGTH_SHORT).show()
            return
        }

        studentList.add(Student(mssv, hoTen))
        adapter.notifyItemInserted(studentList.size - 1)
        android.widget.Toast.makeText(this, "Thêm sinh viên thành công",
            android.widget.Toast.LENGTH_SHORT).show()
        clearInputs()
        selectedPosition = -1
    }

    private fun updateStudent() {
        if (selectedPosition == -1) {
            android.widget.Toast.makeText(this, "Vui lòng chọn sinh viên để cập nhật",
                android.widget.Toast.LENGTH_SHORT).show()
            return
        }

        val hoTen = etHoTen.text.toString().trim()
        if (hoTen.isEmpty()) {
            android.widget.Toast.makeText(this, "Vui lòng nhập họ tên",
                android.widget.Toast.LENGTH_SHORT).show()
            return
        }

        studentList[selectedPosition].hoTen = hoTen
        adapter.notifyItemChanged(selectedPosition)
        android.widget.Toast.makeText(this, "Cập nhật thành công",
            android.widget.Toast.LENGTH_SHORT).show()
        clearInputs()
        selectedPosition = -1
        resetButtons()
    }

    private fun onStudentSelected(position: Int) {
        selectedPosition = position
        val student = studentList[position]
        etMSSV.setText(student.mssv)
        etHoTen.setText(student.hoTen)
        etMSSV.isEnabled = false
        btnUpdate.isEnabled = true
        btnAdd.isEnabled = false
    }

    fun deleteStudent(position: Int) {
        studentList.removeAt(position)
        adapter.notifyItemRemoved(position)
        clearInputs()
        selectedPosition = -1
        resetButtons()
    }

    private fun clearInputs() {
        etMSSV.text.clear()
        etHoTen.text.clear()
    }

    private fun resetButtons() {
        etMSSV.isEnabled = true
        btnAdd.isEnabled = true
        btnUpdate.isEnabled = false
    }
}
