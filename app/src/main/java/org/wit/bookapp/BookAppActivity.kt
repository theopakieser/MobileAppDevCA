package org.wit.bookapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import org.wit.bookapp.databinding.ActivityBookappBinding
import timber.log.Timber
import timber.log.Timber.i

private lateinit var binding: ActivityBookappBinding

class BookAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookappBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        i("Book App Activity started..")

        binding.btnAdd.setOnClickListener() {
            val bookTitle = binding.bookTitle.text.toString()
            if (bookTitle.isNotEmpty()) {
                i("add Button Pressed: $bookTitle")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }



    }
}
