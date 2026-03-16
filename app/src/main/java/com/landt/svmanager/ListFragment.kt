package com.landt.svmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.landt.svmanager.databinding.ListFragmentBinding

class ListFragment : Fragment() {
    private var _binding: ListFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]


        val danhSach = sharedViewModel.danhSachSv
        val danhSachHienThi = danhSach.map { "${it.maSv} - ${it.name}" }.toMutableList()

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, danhSachHienThi)
        binding.lvStudent.adapter = adapter

        binding.lvStudent.setOnItemClickListener { _, _, position, _ ->
            // Lấy sinh viên tại vị trí được click
            val studentToEdit = sharedViewModel.danhSachSv[position]

            // Tạo layout chứa 2 ô nhập liệu cho Dialog
            val layout = android.widget.LinearLayout(requireContext()).apply {
                orientation = android.widget.LinearLayout.VERTICAL
                setPadding(60, 40, 60, 10)
            }

            val edtMaSv = android.widget.EditText(requireContext()).apply {
                setText(studentToEdit.maSv) // Gắn mã SV cũ vào
                hint = "Mã SV"
            }
            val edtName = android.widget.EditText(requireContext()).apply {
                setText(studentToEdit.name) // Gắn tên cũ vào
                hint = "Họ và tên"
            }

            layout.addView(edtMaSv)
            layout.addView(edtName)

            // Hiển thị AlertDialog
            android.app.AlertDialog.Builder(requireContext())
                .setTitle("Sửa thông tin sinh viên")
                .setView(layout)
                .setPositiveButton("Lưu") { _, _ ->
                    val newMaSv = edtMaSv.text.toString()
                    val newName = edtName.text.toString()

                    if (newMaSv.isNotEmpty() && newName.isNotEmpty()) {
                        // 1. Cập nhật dữ liệu gốc trong ViewModel
                        sharedViewModel.danhSachSv[position] = Student(newMaSv, newName)

                        // 2. Cập nhật dữ liệu hiển thị của Adapter
                        danhSachHienThi[position] = "$newMaSv - $newName"

                        // 3. Thông báo cho Adapter biết dữ liệu đã thay đổi để vẽ lại UI
                        adapter.notifyDataSetChanged()

                        android.widget.Toast.makeText(requireContext(), "Cập nhật thành công!", android.widget.Toast.LENGTH_SHORT).show()
                    } else {
                        android.widget.Toast.makeText(requireContext(), "Không được để trống thông tin", android.widget.Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Hủy", null)
                .show()
        }

        binding.btnQuayLai.setOnClickListener {

            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.btnXoaDs.setOnClickListener {
            // Xóa danh sách trong ViewModel
            sharedViewModel.danhSachSv.clear()
            adapter.clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}