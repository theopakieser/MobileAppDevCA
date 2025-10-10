package org.wit.bookapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.bookapp.databinding.ActivityBookappBinding
import org.wit.bookapp.models.BookModel
import timber.log.Timber.i

class BookAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookappBinding
    var book = BookModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        i("Book App Activity started...")

        binding.btnAdd.setOnClickListener {
            book.title = binding.bookTitle.text.toString()
            book.author = binding.bookAuthor.text.toString()
            book.genre = binding.bookGenre.text.toString()
            book.pages = binding.bookPages.text.toString().toIntOrNull() ?: 0

            if (book.title.isNotEmpty() && book.author.isNotEmpty()) {
                i("Add Button Pressed: $book")

                val resultIntent = Intent()
                resultIntent.putExtra("book_added", book)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Snackbar.make(it, "Please enter at least a title and author", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
