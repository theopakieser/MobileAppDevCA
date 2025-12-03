package org.wit.bookapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.bookapp.R
import org.wit.bookapp.databinding.CardBookBinding
import org.wit.bookapp.models.BookModel

class BookAdapter(
    private var books: List<BookModel>,
    private val listener: BookListener
) : RecyclerView.Adapter<BookAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val book = books[holder.adapterPosition]
        holder.bind(book, listener)
    }

    override fun getItemCount(): Int = books.size

    class MainHolder(private val binding: CardBookBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: BookModel, listener: BookListener) {
            binding.bookTitle.text = book.title
            binding.bookAuthor.text = book.author
            binding.bookNotes.text = book.notes
            binding.bookRating.rating = book.rating.toFloat()

            if (book.image.isNotEmpty()) {
                binding.bookCover.setImageURI(Uri.parse(book.image))
            } else {
                binding.bookCover.setImageResource(R.drawable.ic_book_placeholder)
            }

            binding.root.setOnClickListener { listener.onBookClick(book) }
        }
    }
}