package org.wit.bookapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.bookapp.databinding.ActivityBookappBinding
import org.wit.bookapp.models.BookModel
import timber.log.Timber
import timber.log.Timber.i

class BookAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookappBinding
    val books = ArrayList<BookModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Book App started...")

        binding.btnAdd.setOnClickListener() {
            val book = BookModel( title = binding.bookTitle.text.toString(),
                author = binding.bookAuthor.text.toString(),
                genre = binding.bookGenre.text.toString(),
                pages = binding.bookPages.text.toString().toIntOrNull() ?: 0
            )
            if (book.title.isNotEmpty() && book.author.isNotEmpty() && book.genre.isNotEmpty() && book.pages > 0) {
                books.add(book)
                i("Book Added: ${book.title} by ${book.author}. \n This is a ${book.genre} book, it is ${book.pages} pages long")
                i("Total Books: ${books.size}")
            }
            else {
                Snackbar
                    .make(it,"Please fill in all required fields", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
