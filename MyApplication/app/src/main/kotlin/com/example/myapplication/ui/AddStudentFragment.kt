package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentAddStudentBinding
import com.example.myapplication.model.Student
import com.example.myapplication.viewmodel.StudentViewModel
import kotlinx.coroutines.launch

class AddStudentFragment : Fragment() {

    private var _binding: FragmentAddStudentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSave.setOnClickListener {
            val mssv = binding.etMSSV.text.toString().trim()
            val hoTen = binding.etHoTen.text.toString().trim()
            val soDienThoai = binding.etSoDienThoai.text.toString().trim()
            val diaChi = binding.etDiaChi.text.toString().trim()

            if (validateInput(mssv, hoTen, soDienThoai, diaChi)) {
                // Kiểm tra MSSV đã tồn tại trong database
                lifecycleScope.launch {
                    val exists = viewModel.checkStudentExists(mssv)
                    if (exists) {
                        binding.etMSSV.error = "MSSV đã tồn tại trong database"
                        binding.etMSSV.requestFocus()
                    } else {
                        val newStudent = Student(mssv, hoTen, soDienThoai, diaChi)
                        viewModel.addStudent(newStudent)

                        Toast.makeText(
                            requireContext(),
                            "Đang lưu vào database...",
                            Toast.LENGTH_SHORT
                        ).show()

                        findNavController().navigateUp()
                    }
                }
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun validateInput(
        mssv: String,
        hoTen: String,
        soDienThoai: String,
        diaChi: String
    ): Boolean {
        if (mssv.isEmpty()) {
            binding.etMSSV.error = "Vui lòng nhập MSSV"
            binding.etMSSV.requestFocus()
            return false
        }

        if (hoTen.isEmpty()) {
            binding.etHoTen.error = "Vui lòng nhập họ tên"
            binding.etHoTen.requestFocus()
            return false
        }

        if (soDienThoai.isEmpty()) {
            binding.etSoDienThoai.error = "Vui lòng nhập số điện thoại"
            binding.etSoDienThoai.requestFocus()
            return false
        }

        if (diaChi.isEmpty()) {
            binding.etDiaChi.error = "Vui lòng nhập địa chỉ"
            binding.etDiaChi.requestFocus()
            return false
        }

        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}