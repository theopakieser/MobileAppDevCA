package org.wit.bookapp.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.ajalt.timberkt.Timber.i
import com.google.android.material.snackbar.Snackbar
import org.wit.bookapp.R
import org.wit.bookapp.databinding.ActivityBookappBinding
import org.wit.bookapp.main.MainApp
import org.wit.bookapp.models.BookModel
import timber.log.Timber.i

/**
 * BookAppActivity handles adding and editing individual books.
 * Includes form validation and save/cancel options.
 */

class BookAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookappBinding
    private lateinit var app: MainApp
    var book = BookModel()
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookappBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = getString(R.string.app_name)
        setSupportActionBar(binding.toolbar)


        app = application as MainApp
        i("Book App Activity started...")

        if (intent.hasExtra("book_edit")) {
            edit = true
            book = intent.getParcelableExtra("book_edit")!!
            binding.bookTitle.setText(book.title)
            binding.bookAuthor.setText(book.author)
            binding.bookGenre.setText(book.genre)
            binding.bookPages.setText(book.pages.toString())
            binding.btnAdd.text = getString(R.string.button_save)
        }





        binding.btnAdd.setOnClickListener {
            book.title = binding.bookTitle.text.toString()
            book.author = binding.bookAuthor.text.toString()
            book.genre = binding.bookGenre.text.toString()
            book.pages = binding.bookPages.text.toString().toIntOrNull() ?: 0

            if (book.title.isNotEmpty() && book.author.isNotEmpty()) {
                if (edit) {
                    app.books.update(book)  //update existing
                } else {
                    app.books.create(book)  //create new
                }
                setResult(RESULT_OK)
                finish()
            } else {
                //load from strings.xml
                Snackbar.make(
                    it,
                    getString(R.string.error_enter_title_author),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_book, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                setResult(RESULT_CANCELED)
                finish()  //close add/edit screen
            }
        }
        return super.onOptionsItemSelected(item)
    }

}


