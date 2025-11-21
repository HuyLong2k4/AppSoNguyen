package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val studentList: List<Student>,
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvHoTen: TextView = itemView.findViewById(R.id.tv_ho_ten)
        private val tvMSSV: TextView = itemView.findViewById(R.id.tv_mssv)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete)

        fun bind(student: Student, position: Int) {
            tvHoTen.text = student.hoTen
            tvMSSV.text = student.mssv

            itemView.setOnClickListener {
                onItemClick(position)
            }

            btnDelete.setOnClickListener {
                (itemView.context as MainActivity).deleteStudent(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position], position)
    }

    override fun getItemCount() = studentList.size
}