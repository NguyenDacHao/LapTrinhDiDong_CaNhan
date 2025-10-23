package com.example.qltv.viewmodel

import androidx.lifecycle.ViewModel
import com.example.qltv.model.Book
import com.example.qltv.model.LibraryManager
import com.example.qltv.model.Student
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LibraryViewModel(
    // Khởi tạo LibraryManager để quản lý data và logic
    private val manager: LibraryManager = LibraryManager()
) : ViewModel() {

    // Trạng thái sinh viên hiện tại - UI sẽ quan sát (collect)
    private val _currentStudent = MutableStateFlow(manager.getCurrentStudent())
    val currentStudent: StateFlow<Student> = _currentStudent

    // Trạng thái danh sách sách đã mượn
    private val _borrowedBooks = MutableStateFlow(manager.getBorrowedBooksByCurrentStudent())
    val borrowedBooks: StateFlow<List<Book>> = _borrowedBooks

    // Cập nhật tất cả trạng thái UI sau khi logic nghiệp vụ thay đổi
    private fun updateState() {
        // Cần tạo bản sao (copy) để đảm bảo StateFlow nhận biết được sự thay đổi
        _currentStudent.value = manager.getCurrentStudent().copy()
        _borrowedBooks.value = manager.getBorrowedBooksByCurrentStudent()
    }

    /**
     * Xử lý sự kiện "Thay đổi" sinh viên.
     */
    fun changeStudent() {
        manager.changeStudent()
        updateState()
    }

    /**
     * Xử lý sự kiện "Thêm" (mượn sách).
     */
    fun borrowBook() {
        manager.borrowNewBook()
        updateState()
    }
}