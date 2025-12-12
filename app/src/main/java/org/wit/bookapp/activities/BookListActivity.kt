package org.wit.bookapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.bookapp.adapters.BookAdapter
import org.wit.bookapp.adapters.BookListener
import org.wit.bookapp.databinding.ActivityBookListBinding
import org.wit.bookapp.main.MainApp
import org.wit.bookapp.models.BookModel

class BookListActivity : AppCompatActivity(), BookListener {

    private lateinit var binding: ActivityBookListBinding
    private lateinit var app: MainApp

    private val editBookLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val updatedBook =
                    result.data?.getParcelableExtra<BookModel>("book")
                if (updatedBook != null) {
                    app.books.update(updatedBook)
                    loadBooks()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        loadBooks()

        binding.fab.setOnClickListener {
            val intent = Intent(this, BookActivity::class.java)
            addBookLauncher.launch(intent)
        }

        binding.btnFindBookstores.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }

    }

    private val addBookLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val newBook = result.data?.getParcelableExtra<BookModel>("book")
                if (newBook != null) {
                    app.books.create(newBook)
                    loadBooks()
                }
            }
        }


    override fun onBookClick(book: BookModel) {
        val intent = Intent(this, BookActivity::class.java)
        intent.putExtra("book", book)
        editBookLauncher.launch(intent)
    }

    private fun loadBooks() {
        binding.recyclerView.adapter = BookAdapter(app.books.findAll(), this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}
