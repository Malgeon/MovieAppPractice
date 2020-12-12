package com.example.movieapppractice.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapppractice.data.model.BaseResponse
import com.example.movieapppractice.data.model.CommentList
import com.example.movieapppractice.data.model.MovieDetail
import com.example.movieapppractice.data.repository.Repository
import com.example.movieapppractice.ui.base.BaseViewModel
import com.example.movieapppractice.util.ext.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieItemViewModel(
    private val appRepository: Repository
) : BaseViewModel() {

    private val TAG = "MovieItemViewModel"

    private val _movieItemsLiveData = MutableLiveData<BaseResponse<MovieDetail>>()
    val movieItemsLiveData: LiveData<BaseResponse<MovieDetail>>
        get() = _movieItemsLiveData

    private val _commentLiveData = MutableLiveData<BaseResponse<CommentList>>()
    val commentLiveData: LiveData<BaseResponse<CommentList>>
        get() = _commentLiveData

    fun loadItem(movieId: Int) {
        appRepository.getMovieDetailData(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _movieItemsLiveData.postValue(it)
            }, {
                // 지금은 단순히 무슨 Error인지 확인용으로만 만들고, 나중에 LiveData로 자동 reFresh 되도록 만들자.
                Log.d(TAG, "Throwable")
            }).addTo(compositeDisposable)
    }

    fun loadComment(movieId: Int, startIndex: Int, length: Int) {
        appRepository.getCommentData(movieId, startIndex, length)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _commentLiveData.postValue(it)
            }, {
                // 지금은 단순히 무슨 Error인지 확인용으로만 만들고, 나중에 LiveData로 자동 reFresh 되도록 만들자.
                Log.d(TAG, "Throwable")
            }).addTo(compositeDisposable)
    }

}