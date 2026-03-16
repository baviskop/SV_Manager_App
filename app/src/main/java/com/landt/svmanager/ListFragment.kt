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

        // Lấy lại ViewModel đã được khởi tạo ở Activity
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        // Lấy danh sách từ ViewModel
        val danhSach = sharedViewModel.danhSachSv
        val danhSachHienThi = danhSach.map { "${it.maSv} - ${it.name}" }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, danhSachHienThi)
        binding.lvStudent.adapter = adapter

        binding.btnQuayLai.setOnClickListener {
            // Quay lại Fragment trước đó
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}