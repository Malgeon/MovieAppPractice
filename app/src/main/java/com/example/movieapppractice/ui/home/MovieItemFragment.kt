package com.example.movieapppractice.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movieapppractice.R
import com.example.movieapppractice.ui.base.BaseFragment

class MovieItemFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_item, container, false)
    }




    override fun observeChange() {
        TODO("Not yet implemented")
    }


    companion object {
        fun newInstance() =
            MovieItemFragment()
    }
}