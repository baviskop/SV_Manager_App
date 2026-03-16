package com.landt.svmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.landt.svmanager.databinding.InputFragmentBinding

class InputFragment : Fragment() {
    private var _binding: InputFragmentBinding? = null
    private val binding get() = _binding!!

    // Gọi SharedViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = InputFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Khởi tạo ViewModel gắn với Activity chứa Fragment này
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        binding.apply {
            btnThem.setOnClickListener {
                val maSv = edtMaSv.text.toString()
                val name = edtName.text.toString()
                if (maSv.isEmpty() || name.isEmpty()) {
                    Toast.makeText(requireContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                } else {
                    // Thêm vào danh sách trong ViewModel
                    sharedViewModel.danhSachSv.add(Student(maSv, name))
                    Toast.makeText(requireContext(), "Đã thêm: $maSv - $name", Toast.LENGTH_SHORT).show()
                    edtMaSv.text.clear()
                    edtName.text.clear()
                }
            }

            btnXemDs.setOnClickListener {
                // Chuyển sang ListFragment
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ListFragment())
                    .addToBackStack(null) // Cho phép ấn nút Back của điện thoại để quay lại
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Tránh memory leak
    }
}