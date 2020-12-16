package com.example.movieapppractice.ui.home

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.movieapppractice.R
import com.example.movieapppractice.databinding.FragmentRatingBinding
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class RatingFragment(
    private val movieId: Int,
    private val title: String,
    private val writerId: String
) : DialogFragment() {

    lateinit var binding: FragmentRatingBinding
    private val viewModel by sharedViewModel<MovieItemViewModel>()
    private var sendFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRatingBinding.inflate(inflater, container, false)
        isCancelable = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            movieTitle.text = title
            cancelButton.setOnClickListener { dismiss() }
            saveButton.setOnClickListener {
                viewModel.postComment(movieId, writerId, ratingBar.rating, contentsInput.text.toString())
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(id: Int, title: String, writer: String) = RatingFragment(id, title, writer)
    }
}