package com.example.qltv.model



/**
 * Lớp quản lý nghiệp vụ, đóng gói logic về sinh viên và sách.
 */
class LibraryManager {
    // 1. Tạo danh sách sinh viên ban đầu
    private val allStudents = mutableListOf(
        Student(1, "Nguyen Van A", mutableListOf(1, 2)), // Mượn Sách 01, Sách 02
        Student(2, "Nguyen Thi B", mutableListOf(1)),    // Mượn Sách 01
        Student(3, "Nguyen Van C", mutableListOf())      // Chưa mượn sách
    )

    // 2. Tạo danh sách sách ban đầu
    private val allBooks = mutableListOf(
        Book(1, "Sách 01", "Tác giả X"),
        Book(2, "Sách 02", "Tác giả Y"),
        Book(3, "Sách 03", "Tác giả Z"),
        Book(4, "Sách 04", "Tác giả P"),
        Book(5, "Sách 05", "Tác giả Q")
    )

    // Trạng thái sinh viên đang hoạt động (Mặc định là người đầu tiên)
    private var currentStudent = allStudents.first()

    fun getCurrentStudent(): Student = currentStudent

    /**
     * Thay đổi sinh viên: chuyển đổi tuần tự A -> B -> C -> A.
     */
    fun changeStudent() {
        // Tìm index của sinh viên hiện tại, sau đó chuyển sang người tiếp theo
        val currentIndex = allStudents.indexOfFirst { it.id == currentStudent.id }
        val nextIndex = (currentIndex + 1) % allStudents.size
        currentStudent = allStudents[nextIndex]
    }

    /**
     * Lấy danh sách sách (đối tượng Book) mà sinh viên hiện tại đã mượn.
     */
    fun getBorrowedBooksByCurrentStudent(): List<Book> {
        return allBooks.filter { book ->
            currentStudent.borrowedBookIds.contains(book.id)
        }
    }

    /**
     * Giả lập chức năng "Thêm/Mượn" một cuốn sách mới (chưa mượn).
     */
    fun borrowNewBook() {
        // Tìm cuốn sách có sẵn đầu tiên mà sinh viên chưa mượn
        val bookToBorrow = allBooks.firstOrNull { book ->
            !currentStudent.borrowedBookIds.contains(book.id)
        }

        bookToBorrow?.let {
            currentStudent.borrowedBookIds.add(it.id)
        }
    }
}