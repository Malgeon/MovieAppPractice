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

class RatingViewModel(
    private val appRepository: Repository
) : BaseViewModel() {
    private val TAG = "RatingViewModel"

    fun postComment(movieId: Int, title: String, rating: Float, contents: String) {
        appRepository.postComment(movieId, title, rating, contents)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, it.result) /* DialogFragment라서, BaseFragment를 가져오지 못해 함수를 다시작성해야 한다... 그래서 귀찮으니 Log로 대체
                                            다시 작성하지 않고 가져올수 있는 방법을 다음에 알아보자.
                                        */
            }, {
                Log.d(TAG, "Throwable")
            }).addTo(compositeDisposable)
    }

}