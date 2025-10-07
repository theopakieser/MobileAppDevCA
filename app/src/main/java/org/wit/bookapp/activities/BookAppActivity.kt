package org.wit.bookapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.bookapp.databinding.ActivityBookappBinding
import org.wit.bookapp.main.MainApp
import org.wit.bookapp.models.BookModel
import timber.log.Timber.i

class BookAppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookappBinding
    var book = BookModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Book App Activity started...")
        binding.btnAdd.setOnClickListener() {
            book.title = binding.bookTitle.text.toString()
            book.author = binding.bookAuthor.text.toString()
            book.genre = binding.bookGenre.text.toString()
            book.pages = binding.bookPages.text.toString().toIntOrNull() ?: 0
            if (book.title.isNotEmpty() && book.author.isNotEmpty()) {
                app.books.add(book.copy())
                i("add Button Pressed: ${book}")
                for (i in app.books.indices) {
                    i("Placemark[$i]:${app.books[i]}")
                }
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}


