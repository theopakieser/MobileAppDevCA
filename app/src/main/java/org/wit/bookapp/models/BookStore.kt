package org.wit.bookapp.models

interface BookStore {
    fun findAll(): List<BookModel>
    fun findById(id : Long): BookModel?
    fun create(book: BookModel)
    fun update(book: BookModel)
    fun delete(book: BookModel)
}
