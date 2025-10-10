package org.wit.bookapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.wit.bookapp.R
import org.wit.bookapp.databinding.ActivityBookListBinding
import org.wit.bookapp.databinding.CardBookBinding
import org.wit.bookapp.main.MainApp
import org.wit.bookapp.models.BookModel

class BookListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityBookListBinding

    // Launcher for Add Book screen (handles result)
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                val newBook = it.data!!.getSerializableExtra("book_added") as BookModel
                app.books.add(newBook)
                binding.recyclerView.adapter =
                    BookAdapter(app.books.toMutableList()) // refresh list
                Snackbar.make(binding.root, "Added: ${newBook.title}", Snackbar.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "Books"
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        // RecyclerView setup
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = BookAdapter(app.books.toMutableList())

        // FAB to launch Add Book screen
        binding.fab.setOnClickListener {
            val launcherIntent = Intent(this, BookAppActivity::class.java)
            getResult.launch(launcherIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, BookAppActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}


class BookAdapter constructor(private var books: MutableList<BookModel>)
    : RecyclerView.Adapter<BookAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val book = books[holder.adapterPosition]
        holder.bind(book)

        //long press to delete
        holder.itemView.setOnLongClickListener {
            val removedBook = books[holder.adapterPosition]
            books.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)

            Snackbar.make(holder.itemView, "Deleted: ${removedBook.title}", Snackbar.LENGTH_SHORT).show()
            true
        }
    }

    override fun getItemCount(): Int = books.size

    class MainHolder(private val binding: CardBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: BookModel) {
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            binding.bookGenre.text = book.genre
            binding.bookPages.text = book.pages.toString()
        }
    }
}
