package org.wit.bookapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.bookapp.R
import org.wit.bookapp.databinding.ActivityBookListBinding
import org.wit.bookapp.databinding.CardBookBinding
import org.wit.bookapp.main.MainApp
import org.wit.bookapp.models.BookModel

class BookListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityBookListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = BookAdapter(app.books)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}


class BookAdapter constructor(private var books: List<BookModel>) :
    RecyclerView.Adapter<BookAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardBookBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = books[holder.adapterPosition]
        holder.bind(placemark)
    }

    override fun getItemCount(): Int = books.size

    class MainHolder(private val binding : CardBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book : BookModel) {
           binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.title
            binding.bookGenre.text = book.genre
            binding.bookPages.text = book.pages.toString()
        }
    }
}
