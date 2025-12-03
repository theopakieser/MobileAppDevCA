package org.wit.bookapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import org.wit.bookapp.R
import org.wit.bookapp.databinding.ActivityBookappBinding
import org.wit.bookapp.models.BookModel

class BookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookappBinding
    private var book = BookModel()
    private var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("book_edit")) {
            edit = true
            book = intent.getParcelableExtra("book_edit")!!

            binding.bookTitle.setText(book.title)
            binding.bookAuthor.setText(book.author)
            binding.bookNotes.setText(book.notes)
            binding.bookRating.rating = book.rating.toFloat()

            binding.btnAdd.text = "Save Book"
        }

        binding.btnAdd.setOnClickListener {
            book.title = binding.bookTitle.text.toString()
            book.author = binding.bookAuthor.text.toString()
            book.notes = binding.bookNotes.text.toString()
            book.rating = binding.bookRating.rating.toInt()

            val resultIntent = Intent()
            resultIntent.putExtra("book_update", book)
            setResult(RESULT_OK, resultIntent)
        }
    }

}
