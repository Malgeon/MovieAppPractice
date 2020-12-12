package com.example.movieapppractice.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapppractice.data.model.BaseResponse
import com.example.movieapppractice.data.model.MovieData
import com.example.movieapppractice.data.repository.Repository
import com.example.movieapppractice.ui.base.BaseViewModel
import com.example.movieapppractice.util.ext.addTo
import com.example.movieapppractice.util.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel (
    private val appRepository: Repository
    ) : BaseViewModel() {

    private val TAG = "HomeViewModel"
    private val _pagerHomeLiveData = MutableLiveData<BaseResponse<MovieData>>()
    val pagerHomeLiveData: LiveData<BaseResponse<MovieData>>
        get() = _pagerHomeLiveData


    fun loadHome(type: Int) {
        appRepository.getHomeData(type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _pagerHomeLiveData.postValue(it)
            }, {
                // 지금은 단순히 무슨 Error인지 확인용으로만 만들고, 나중에 LiveData로 자동 reFresh 되도록 만들자.
                Log.d(TAG, "Throwable")
            }).addTo(compositeDisposable)
    }
}