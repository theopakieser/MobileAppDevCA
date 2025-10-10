package org.wit.bookapp.main

import android.app.Application
import org.wit.bookapp.models.BookModel
import timber.log.Timber
import timber.log.Timber.i


class MainApp : Application() {
    val books = ArrayList<BookModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Book App started")
       // books.add(BookModel("One", "Author", "Genre", 1))
      //  books.add(BookModel("Two", "Author", "Genre", 2))
      //  books.add(BookModel("Three", "Author", "Genre", 3))

    }
}
