package org.wit.bookapp.adapters

import org.wit.bookapp.models.BookModel

interface BookListener {
    fun onBookClick(book: BookModel)
}