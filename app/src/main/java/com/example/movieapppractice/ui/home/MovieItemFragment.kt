package com.example.movieapppractice.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapppractice.data.model.BaseResponse
import com.example.movieapppractice.data.model.CommentList
import com.example.movieapppractice.data.model.MovieDetail
import com.example.movieapppractice.databinding.FragmentMovieItemBinding
import com.example.movieapppractice.ui.adapter.CommentAdapter
import com.example.movieapppractice.ui.adapter.ItemImagesAdapter
import com.example.movieapppractice.ui.base.BaseFragment
import com.example.movieapppractice.util.ext.observe
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class MovieItemFragment(private var movieId: Int) : BaseFragment() {

    private val viewModel by sharedViewModel<MovieItemViewModel>()
    lateinit var binding: FragmentMovieItemBinding
    private var presentComment = 3

    private val imageAdapter by lazy {
        ItemImagesAdapter()
    }
    private val commentAdapter by lazy {
        CommentAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieItemBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadItem(movieId)
        viewModel.loadComment(movieId, 0, presentComment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.fragmentToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }

        with(binding) {
            root.context?.let {
                imageRecyclerView.layoutManager = LinearLayoutManager(it, LinearLayoutManager.HORIZONTAL, false)
                imageRecyclerView.adapter = imageAdapter
                commentRecyclerView.layoutManager = LinearLayoutManager(it)
                commentRecyclerView.adapter = commentAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Fragment", "Destroy")
    }

    override fun observeChange() {
        observe(viewModel.movieItemsLiveData, ::onItemLoaded)
        observe(viewModel.commentLiveData, ::onCommentLoaded)
    }

    private fun onCommentLoaded(items: BaseResponse<CommentList>) {
        items.totalCount?.let {
            binding.commentCount.text = "$it"
        }
        commentAdapter.addItem(items.result)
    }

    private fun onItemLoaded(items: BaseResponse<MovieDetail>) {
        loadView(items.result[0])
        onDataLoaded(items.result[0].photos)
    }

    private fun onDataLoaded(imageUrl: String?) {
        /*
         프로그래먼트로 만들게 되니까, 프래그먼트를 재사용하게 되어 null값에 해당하는 경우 이전 프래그먼트 값을 가지고 있게 된다.
         이전 값을 가지고 있는 것은 loading progress로 안보여주면 해결 가능.
         */

        imageUrl?.let {
            val imageUrls = it.split(",")
            binding.imageCount.text = "${imageUrls.size}"
            imageAdapter.addItem(imageUrls)
        } ?: run {
            binding.imageCount.text = "0"
            imageAdapter.addItem(null)
        }
    }

    private fun loadView(movieDetail: MovieDetail) {
        with(binding) {
            Picasso.get().load(movieDetail.image).into(itemImage)
            itemTitle.text = movieDetail.title
            movieGrade.text = "${movieDetail.grade}세"
            likeCount.text = "${movieDetail.like}"
            dislikeCount.text = "${movieDetail.dislike}"
            synopsis.text = movieDetail.synopsis
            movieDirector.text = "감독: ${movieDetail.director}"
            movieActor.text = "출연: ${movieDetail.actor}"
            movieDetail.outlinks?.let {
                movieOutlinks.visibility = View.VISIBLE
                movieOutlinks.text = "개요: $it"
            }
        }
    }


    companion object {
        fun newInstance(movieId: Int) = MovieItemFragment(movieId)
    }
}