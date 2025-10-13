package org.wit.bookapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.wit.bookapp.R
import org.wit.bookapp.adapters.BookAdapter
import org.wit.bookapp.adapters.BookListener
import org.wit.bookapp.databinding.ActivityBookListBinding
import org.wit.bookapp.main.MainApp
import org.wit.bookapp.models.BookModel

class BookListActivity : AppCompatActivity(), BookListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityBookListBinding
    private lateinit var adapter: BookAdapter

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                adapter = BookAdapter(app.books.findAll().toMutableList(), this)
                binding.recyclerView.adapter = adapter
                Snackbar.make(binding.root, "Book Saved!", Snackbar.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "Books"
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        adapter = BookAdapter(app.books.findAll().toMutableList(), this)
        binding.recyclerView.adapter = adapter

        binding.fab.setOnClickListener {
            val launcherIntent = Intent(this, BookAppActivity::class.java)
            getResult.launch(launcherIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onBookClick(book: BookModel) {
        val launcherIntent = Intent(this, BookAppActivity::class.java)
        launcherIntent.putExtra("book_edit", book)
        getResult.launch(launcherIntent)
    }
}



