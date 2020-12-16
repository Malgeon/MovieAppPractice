package com.example.movieapppractice.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapppractice.data.model.BaseResponse
import com.example.movieapppractice.data.model.CommentList
import com.example.movieapppractice.data.model.MovieDetail
import com.example.movieapppractice.data.repository.Repository
import com.example.movieapppractice.ui.base.BaseViewModel
import com.example.movieapppractice.util.SingleLiveEvent
import com.example.movieapppractice.util.ext.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieItemViewModel(
    private val appRepository: Repository
) : BaseViewModel() {

    private val TAG = "MovieItemViewModel"

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _toastMessage = SingleLiveEvent<String>()
    val toastMessage: LiveData<String>
        get() = _toastMessage

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

    fun postLikeOrDisLike(movieId: Int, isLike: String?, isDislike: String?) {
        appRepository.postLikeOrDislike(movieId, isLike, isDislike)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _toastMessage.value = it.result
            }, {
                Log.d(TAG, "Throwable")
            }).addTo(compositeDisposable)
    }

    fun postComment(movieId: Int, title: String, rating: Float, contents: String) {
        appRepository.postComment(movieId, title, rating, contents)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, it.result)
                _toastMessage.value = it.result
                /* DialogFragment라서, BaseFragment를 가져오지 못해 함수를 다시작성해야 한다... 그래서 귀찮으니 Log로 대체
                    다시 작성하지 않고 가져올수 있는 방법을 다음에 알아보자.
                */
            }, {
                Log.d(TAG, "Throwable")
            }).addTo(compositeDisposable)
    }

}