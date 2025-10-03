package org.wit.bookapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import timber.log.Timber.i

class BookAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookapp)

        Timber.plant(Timber.DebugTree())
        i("Book App Activity started..")
    }
}
