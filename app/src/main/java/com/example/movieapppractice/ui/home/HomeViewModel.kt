package com.example.movieapppractice.ui.home

import android.util.Log
import com.example.movieapppractice.data.repository.Repository
import com.example.movieapppractice.ui.base.BaseViewModel
import com.example.movieapppractice.util.ext.addTo
import com.example.movieapppractice.util.rx.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel (
    private val appRepository: Repository
    ) : BaseViewModel() {

    private val TAG = "MainViewModel"


    fun loadHome() {
        appRepository.getHomeData(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                it.run{
                    Log.d(TAG, "code : $this")
                }
            }, {

                Log.d(TAG, "Throwable")
            }).addTo(compositeDisposable)
    }
}