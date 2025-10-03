package org.wit.bookapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class BookAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookapp)

        Timber.plant(Timber.DebugTree())
        Timber.i("Book App Activity started..")
    }
}
