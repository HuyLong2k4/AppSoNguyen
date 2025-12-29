package com.example.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentUpdateStudentBinding
import com.example.myapplication.model.Student
import com.example.myapplication.viewmodel.StudentViewModel

class UpdateStudentFragment : Fragment() {

    private var _binding: FragmentUpdateStudentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StudentViewModel by activityViewModels()
    private var currentStudent: Student? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setupListeners()
    }

    private fun observeViewModel() {
        viewModel.selectedStudent.observe(viewLifecycleOwner) { student ->
            student?.let {
                currentStudent = it
                // Data Binding sẽ tự động điền dữ liệu vào các EditText
                binding.student = it
                binding.executePendingBindings()
            }
        }
    }

    private fun setupListeners() {
        binding.btnUpdate.setOnClickListener {
            val mssv = binding.etMSSV.text.toString().trim()
            val hoTen = binding.etHoTen.text.toString().trim()
            val soDienThoai = binding.etSoDienThoai.text.toString().trim()
            val diaChi = binding.etDiaChi.text.toString().trim()

            if (validateInput(hoTen, soDienThoai, diaChi)) {
                // Tạo đối tượng Student mới với dữ liệu đã cập nhật
                val updatedStudent = Student(mssv, hoTen, soDienThoai, diaChi)
                viewModel.updateStudent(updatedStudent)

                Toast.makeText(
                    requireContext(),
                    "Đang cập nhật vào database...",
                    Toast.LENGTH_SHORT
                ).show()

                findNavController().navigateUp()
            }
        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun validateInput(
        hoTen: String,
        soDienThoai: String,
        diaChi: String
    ): Boolean {
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