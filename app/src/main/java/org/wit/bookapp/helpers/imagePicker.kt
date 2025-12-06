package org.wit.bookapp.helpers

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

fun showImagePicker(intentLauncher: ActivityResultLauncher<Intent>, activity: Activity) {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    intent.type = "image/*"
    intentLauncher.launch(intent)
}
