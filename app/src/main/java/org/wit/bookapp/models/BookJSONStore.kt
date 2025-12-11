package org.wit.bookapp.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.Serializable
import java.util.UUID

private const val JSON_FILE = "books.json"
private val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
private val listType = object : TypeToken<ArrayList<BookModel>>() {}.type

fun generateRandomId(): Long = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE

class BookJSONStore(private val context: Context) : BookStore, Serializable {

    private var books = mutableListOf<BookModel>()

    init {
        if (File(context.filesDir, JSON_FILE).exists()) {
            deserialize()
        }
    }

    override fun findAll(): List<BookModel> = books

    override fun findById(id: Long): BookModel? = books.find { it.id == id }


    override fun create(book: BookModel) {
        book.id = generateRandomId()
        books.add(book)
        serialize()
    }

    override fun update(book: BookModel) {
        val foundBook = books.find { it.id == book.id }
        if (foundBook != null) {
            foundBook.title = book.title
            foundBook.author = book.author
            foundBook.notes = book.notes
            foundBook.rating = book.rating
            foundBook.image = book.image
            foundBook.location = book.location
            serialize()
        }
    }

    override fun delete(book: BookModel) {
        books.remove(book)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(books, listType)
        File(context.filesDir, JSON_FILE).writeText(jsonString)
    }

    // safer version that won’t crash on bad JSON or missing file
    private fun deserialize() {
        val file = File(context.filesDir, JSON_FILE)
        if (file.exists()) {
            val jsonString = file.readText()
            if (jsonString.isNotEmpty()) {
                books = Gson().fromJson(jsonString, listType)
                println("✅ Loaded ${books.size} books from JSON")
            } else {
                println("books.json is empty — starting with empty list")
                books = mutableListOf()
            }
        } else {
            println("No books.json found — creating new one on first save")
        }
    }
}
