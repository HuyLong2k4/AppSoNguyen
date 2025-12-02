package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class StudentDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_form)

        val etMSSV = findViewById<EditText>(R.id.et_mssv)
        val etHoTen = findViewById<EditText>(R.id.et_ho_ten)
        val etSDT = findViewById<EditText>(R.id.et_sdt)
        val etDiaChi = findViewById<EditText>(R.id.et_dia_chi)
        val btnSave = findViewById<Button>(R.id.btn_save)

        // Lấy dữ liệu từ Intent
        val student = intent.getSerializableExtra("STUDENT_DATA") as? Student
        val position = intent.getIntExtra("POSITION", -1)

        // Hiển thị dữ liệu lên màn hình
        if (student != null) {
            etMSSV.setText(student.mssv)
            etHoTen.setText(student.hoTen)
            etSDT.setText(student.sdt)
            etDiaChi.setText(student.diaChi)

            etMSSV.isEnabled = false // Không cho sửa MSSV
            btnSave.text = "Cập nhật"
        }

        btnSave.setOnClickListener {
            val updatedHoTen = etHoTen.text.toString()
            val updatedSDT = etSDT.text.toString()
            val updatedDiaChi = etDiaChi.text.toString()

            val updatedStudent = Student(etMSSV.text.toString(), updatedHoTen, updatedSDT, updatedDiaChi)

            val resultIntent = Intent()
            resultIntent.putExtra("UPDATED_STUDENT", updatedStudent)
            resultIntent.putExtra("POSITION", position)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}