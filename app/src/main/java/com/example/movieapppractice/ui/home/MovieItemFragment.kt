package com.example.movieapppractice.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapppractice.R
import com.example.movieapppractice.data.model.BaseResponse
import com.example.movieapppractice.data.model.CommentList
import com.example.movieapppractice.data.model.MovieDetail
import com.example.movieapppractice.databinding.FragmentMovieItemBinding
import com.example.movieapppractice.ui.adapter.CommentAdapter
import com.example.movieapppractice.ui.adapter.ItemImagesAdapter
import com.example.movieapppractice.ui.base.BaseFragment
import com.example.movieapppractice.util.ext.observe
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.sharedViewModel
import kotlin.properties.Delegates


class MovieItemFragment(private var movieId: Int) : BaseFragment() {

    // 이런식으로 할당이 가능하다.
    private var applyMovieId = 0
    private lateinit var applyMovieIdStr: String

    private val myWriter = "안드연습"

    private data class MovieInfo(
        var movieId: Int,
        var movieTitle: String,
        var writerId: String
    )
    private lateinit var movieInfo: MovieInfo

    private val viewModel by sharedViewModel<MovieItemViewModel>()
    lateinit var binding: FragmentMovieItemBinding
    private var presentComment = 3

    private var buttonFlag = false
    private var likeCountValue = 0
    private var dislikeCountValue = 0

    private enum class LikeOrDislikeState {
        NOT, UP, DOWN
    }

    private var likeDislikeState = LikeOrDislikeState.NOT

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
        viewModel.loadItem(movieId)
        viewModel.loadComment(movieId, 0, presentComment)
        return binding.root
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

            // 이 기능은 Throwable 가능성이 높아서 안되도록 구성을 해야 하는데 어떻게 해야 할지 모르겠다.
            likeButton.setOnClickListener {
                likeOrDislikeButton(true)
            }
            dislikeButton.setOnClickListener {
                likeOrDislikeButton(false)
            }
            writeCommentButton.setOnClickListener {
                val fragment = RatingFragment.newInstance(movieInfo.movieId, movieInfo.movieTitle, myWriter)
                fragment.show(requireActivity().supportFragmentManager,"tag")
            }
        }
    }

    private fun likeOrDislikeButton(isLike: Boolean) {
        if(!buttonFlag) {
            buttonFlag = true
            if(isLike) applyLike()
            else applyDislike()
        }
    }

    private fun applyDislike() {
        likeDislikeState = when(likeDislikeState) {
            LikeOrDislikeState.NOT -> {
                showDislike(false)
                viewModel.postLikeOrDisLike(movieId, null, "Y")
                LikeOrDislikeState.DOWN
            }
            LikeOrDislikeState.UP -> {
                showDislike(false)
                viewModel.postLikeOrDisLike(movieId, null, "Y")
                showLike(true)
                viewModel.postLikeOrDisLike(movieId, "N", null)
                LikeOrDislikeState.DOWN
            }
            LikeOrDislikeState.DOWN -> {
                showDislike(true)
                viewModel.postLikeOrDisLike(movieId, null, "N")
                LikeOrDislikeState.NOT
            }
        }
    }

    private fun applyLike() {
        likeDislikeState = when(likeDislikeState) {
            LikeOrDislikeState.NOT -> {
                showLike(false)
                viewModel.postLikeOrDisLike(movieId, "Y", null)
                LikeOrDislikeState.UP
            }
            LikeOrDislikeState.UP -> {
                showLike(true)
                viewModel.postLikeOrDisLike(movieId, "N", null)
                LikeOrDislikeState.NOT
            }
            LikeOrDislikeState.DOWN -> {
                showLike(false)
                viewModel.postLikeOrDisLike(movieId, "Y", null)
                showDislike(true)
                viewModel.postLikeOrDisLike(movieId, null, "N")
                LikeOrDislikeState.UP
            }
        }
    }

    private fun showLike(isCancel: Boolean) {
        if(isCancel) setLikeCountView(--likeCountValue, R.drawable.ic_baseline_add_24)
        else setLikeCountView(++likeCountValue, R.drawable.ic_baseline_add_24_selected)
    }

    private fun setLikeCountView(CountValue: Int, drawableValue: Int) {
        with(binding) {
            likeCount.text = "$CountValue"
            likeButton.setBackgroundResource(drawableValue)
        }
    }
    private fun setDislikeCountView(CountValue: Int, drawableValue: Int) {
        with(binding) {
            dislikeCount.text = "$CountValue"
            dislikeButton.setBackgroundResource(drawableValue)
        }
    }

    private fun showDislike(isCancel: Boolean) {
        if(isCancel) setDislikeCountView(--dislikeCountValue, R.drawable.ic_baseline_remove_24)
        else setDislikeCountView(++dislikeCountValue, R.drawable.ic_baseline_remove_24_selected)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Fragment", "Destroy")
    }

    override fun observeChange() {
        observe(viewModel.movieItemsLiveData, ::onItemLoaded)
        observe(viewModel.commentLiveData, ::onCommentLoaded)
        observe(viewModel.toastMessage, ::buttonResponse)
    }

    private fun buttonResponse(message: String?) {
        buttonFlag = false
        showSnackbarMessage(message)
    }

    private fun onCommentLoaded(items: BaseResponse<CommentList>) {
        items.totalCount?.let {
            binding.commentCount.text = "$it"
        }
        commentAdapter.addItem(items.result)
    }

    private fun onItemLoaded(items: BaseResponse<MovieDetail>) {
        setMovieInfo(items.result[0].id, items.result[0].title, myWriter)
        loadView(items.result[0])
        onDataLoaded(items.result[0].photos)
        likeCountValue = items.result[0].like
        dislikeCountValue = items.result[0].dislike
    }

    private fun setMovieInfo(id: Int, title: String, writer: String) {
        movieInfo = MovieInfo(id, title, writer)
    }

    private fun onDataLoaded(imageUrl: String?) {
        /*
         처음 프래그먼트 동작후 재동작시 두번씩 해당 함수가 실행되는데,
         아마도. viewModel을 DI로 적용을 해주다 보니 재사용을 하는데, 프래그먼트가 만들어지면서 LiveData가 기존 값을 observe하면서 시작하는것 같다.
         이것을 한번만 동작하도록 해결하고 싶은데, 어떻게 해결해야 할지 모르겠다.
         그냥 액티비티로 해야 할까?
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
        fun newInstance(movieId: Int) = MovieItemFragment(movieId).apply { applyMovieId = 1 }
    }
}