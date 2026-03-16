package com.landt.svmanager

import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val danhSachSv = mutableListOf<Student>()
}