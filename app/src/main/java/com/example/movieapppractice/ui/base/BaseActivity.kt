package com.example.movieapppractice.ui.base

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapppractice.R
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeChange()
    }

    abstract fun observeChange()

    fun showSnackbarMessage(message: String?) {
        message?.let {
            val container = try {
                findViewById<FrameLayout>(R.id.container)
            } catch (e: Exception) {
                window.decorView.rootView
            }
            Snackbar.make(container, it, Snackbar.LENGTH_LONG).show()
        }
    }
}