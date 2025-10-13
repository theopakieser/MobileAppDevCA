package org.wit.bookapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.wit.bookapp.databinding.CardBookBinding
import org.wit.bookapp.main.MainApp
import org.wit.bookapp.models.BookModel

interface BookListener {
    fun onBookClick(book: BookModel)
}

class BookAdapter constructor(
    private var books: MutableList<BookModel>,
    private val listener: BookListener
) : RecyclerView.Adapter<BookAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val book = books[holder.adapterPosition]
        holder.bind(book, listener)

        holder.itemView.setOnLongClickListener {
            val context = holder.itemView.context.applicationContext as MainApp
            val removedBook = books[holder.adapterPosition]
            context.books.delete(removedBook)
            books.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
            Snackbar.make(holder.itemView, "Deleted: ${removedBook.title}", Snackbar.LENGTH_SHORT).show()
            true
        }
    }

    override fun getItemCount(): Int = books.size

    class MainHolder(private val binding: CardBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: BookModel, listener: BookListener) {
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            binding.bookGenre.text = book.genre
            binding.bookPages.text = book.pages.toString()

            binding.root.setOnClickListener { listener.onBookClick(book) }
        }
    }
}