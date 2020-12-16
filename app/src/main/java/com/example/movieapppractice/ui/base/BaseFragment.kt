package com.example.movieapppractice.ui.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    private fun getBaseActivity() = activity as BaseActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeChange()
    }

    abstract fun observeChange()

    fun showSnackbarMessage(message: String?) {
        getBaseActivity().showSnackbarMessage(message)
    }

}