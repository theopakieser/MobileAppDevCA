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
    var book = BookModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("Book App started...")

        binding.btnAdd.setOnClickListener() {
            book.title = binding.bookTitle.text.toString()
            if (book.title.isNotEmpty()) {
                i("add Button Pressed: $book.title")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}
