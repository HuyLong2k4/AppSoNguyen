package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_form) // Dùng chung layout form

        val etMSSV = findViewById<EditText>(R.id.et_mssv)
        val etHoTen = findViewById<EditText>(R.id.et_ho_ten)
        val etSDT = findViewById<EditText>(R.id.et_sdt)
        val etDiaChi = findViewById<EditText>(R.id.et_dia_chi)
        val btnSave = findViewById<Button>(R.id.btn_save)

        btnSave.text = "Thêm mới"

        btnSave.setOnClickListener {
            val mssv = etMSSV.text.toString()
            val hoTen = etHoTen.text.toString()
            val sdt = etSDT.text.toString()
            val diaChi = etDiaChi.text.toString()

            if (mssv.isEmpty() || hoTen.isEmpty()) {
                Toast.makeText(this, "Nhập đủ thông tin!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Tạo đối tượng Student mới
            val newStudent = Student(mssv, hoTen, sdt, diaChi)

            // Trả dữ liệu về MainActivity
            val resultIntent = Intent()
            resultIntent.putExtra("NEW_STUDENT", newStudent)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}