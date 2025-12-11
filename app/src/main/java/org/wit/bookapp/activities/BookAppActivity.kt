package org.wit.bookapp.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.bookapp.R
import org.wit.bookapp.databinding.ActivityBookappBinding
import org.wit.bookapp.helpers.showImagePicker
import org.wit.bookapp.models.BookModel
import com.squareup.picasso.Picasso
import org.wit.bookapp.models.Location


class BookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookappBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private var book = BookModel()
    private var edit = false

    private val mapIntentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val newLocation = result.data!!.getParcelableExtra<Location>("location")
                if (newLocation != null) {
                    book.location = newLocation
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        registerImagePickerCallback()

        if (intent.hasExtra("book_edit")) {
            edit = true
            book = intent.getParcelableExtra("book_edit")!!

            binding.bookTitle.setText(book.title)
            binding.bookAuthor.setText(book.author)
            binding.bookNotes.setText(book.notes)
            binding.bookRating.rating = book.rating.toFloat()

            if (book.image.isNotEmpty()) {
                Picasso.get()
                    .load(book.image)
                    .into(binding.bookCover)
            }


            binding.btnAdd.text = getString(R.string.button_save)
        }

        binding.btnChooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher, this)
        }

        binding.btnAdd.setOnClickListener {
            val title = binding.bookTitle.text.toString()
            val author = binding.bookAuthor.text.toString()

            if (title.isEmpty() || author.isEmpty()) {
                Snackbar.make(binding.root, R.string.error_enter_title_author, Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
            }

            book.title = title
            book.author = author
            book.notes = binding.bookNotes.text.toString()
            book.rating = binding.bookRating.rating.toInt()

            val data = Intent()
            data.putExtra("book_update", book)
            setResult(RESULT_OK, data)
            finish()
        }

        binding.btnSetLocation.setOnClickListener {
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", book.location)

            mapIntentLauncher.launch(launcherIntent)
        }

    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val uri = result.data!!.data!!
                    contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    book.image = uri.toString()
                    Picasso.get().load(uri).into(binding.bookCover)
                }
            }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_book, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { finish(); true }
            R.id.item_cancel -> {
                setResult(RESULT_CANCELED)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
