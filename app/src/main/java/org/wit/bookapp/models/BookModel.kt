package org.wit.bookapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookModel(
    var id: Long = 0,
    var title: String = "",
    var author: String = "",
    var notes: String = "",
    var rating: Int = 0,
    var image: String = "",
) : Parcelable

