package org.wit.bookapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.wit.bookapp.R
import org.wit.bookapp.adapters.BookAdapter
import org.wit.bookapp.adapters.BookListener
import org.wit.bookapp.databinding.ActivityBookListBinding
import org.wit.bookapp.main.MainApp
import org.wit.bookapp.models.BookModel
import kotlin.jvm.java

/**
 * BookListActivity displays all saved books.
 * - Supports editing via click
 * - Deletion via long-press
 * - JSON persistence through MainApp
 */


class BookListActivity : AppCompatActivity(), BookListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityBookListBinding

    private val editBookLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val updatedBook = it.data?.getParcelableExtra<BookModel>("book_update")
                if (updatedBook != null) {
                    app.books.update(updatedBook)
                    loadBooks()
                }
            }
        }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK) {
                binding.recyclerView.adapter =
                    BookAdapter(app.books.findAll().toMutableList(), this)
                updateEmptyState()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = getString(R.string.app_name)
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = BookAdapter(app.books.findAll().toMutableList(), this)

        binding.fab.setOnClickListener {
            val launcherIntent = Intent(this, BookAppActivity::class.java)
            getResult.launch(launcherIntent)
        }
    }

    private fun updateEmptyState() {
        if (app.books.findAll().isEmpty()) {
            binding.emptyStateText.animate()
                .alpha(1f)
                .setDuration(250)
                .withStartAction { binding.emptyStateText.visibility = View.VISIBLE }
        } else {
            binding.emptyStateText.animate()
                .alpha(0f)
                .setDuration(250)
                .withEndAction { binding.emptyStateText.visibility = View.GONE }
        }
    }


    override fun onResume() {
        super.onResume()
        binding.recyclerView.adapter = BookAdapter(app.books.findAll().toMutableList(), this)
        updateEmptyState()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onBookClick(book: BookModel) {
        val launcherIntent = Intent(this, BookActivity::class.java)
        launcherIntent.putExtra("book_edit", book)
        editBookLauncher.launch(launcherIntent)
    }



}



