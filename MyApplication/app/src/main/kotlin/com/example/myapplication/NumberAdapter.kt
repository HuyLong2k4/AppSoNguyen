package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NumberAdapter(private var numbers: List<Int>) : RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {

    // Lớp này giữ các view cho mỗi item
    class NumberViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numberTextView: TextView = view.findViewById(R.id.number_text_view)
    }

    // Tạo ViewHolder mới (được gọi bởi layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_number, parent, false)
        return NumberViewHolder(view)
    }

    // Gán dữ liệu cho ViewHolder (được gọi bởi layout manager)
    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.numberTextView.text = numbers[position].toString()
    }

    // Trả về số lượng item trong danh sách
    override fun getItemCount() = numbers.size

    // Hàm để cập nhật dữ liệu và thông báo cho RecyclerView vẽ lại
    fun updateData(newNumbers: List<Int>) {
        numbers = newNumbers
        notifyDataSetChanged()
    }
}