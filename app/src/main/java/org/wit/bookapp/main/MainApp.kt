package org.wit.bookapp.main

import android.app.Application
import org.wit.bookapp.models.BookJSONStore
import org.wit.bookapp.models.BookStore

class MainApp : Application() {

    lateinit var books: BookStore

    override fun onCreate() {
        super.onCreate()
        books = BookJSONStore(applicationContext)
    }
}
