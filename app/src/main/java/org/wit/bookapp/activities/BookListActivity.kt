package org.wit.bookapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.bookapp.databinding.ActivityBookappBinding
import org.wit.bookapp.databinding.CardBookBinding
import org.wit.bookapp.main.MainApp
import org.wit.bookapp.models.BookModel

class BookListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityBookappBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = BookAdapter(app.books)
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
        val book = books[holder.adapterPosition]
        holder.bind(book)
    }

    override fun getItemCount(): Int = books.size

    class MainHolder(private val binding : CardBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: BookModel) {
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            binding.bookGenre.text = book.genre

        }
    }
}
