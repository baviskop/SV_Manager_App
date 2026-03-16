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