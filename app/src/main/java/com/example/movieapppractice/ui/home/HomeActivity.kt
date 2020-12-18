package com.example.movieapppractice.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapppractice.R
import com.example.movieapppractice.data.model.BaseResponse
import com.example.movieapppractice.data.model.MovieData
import com.example.movieapppractice.databinding.ActivityHomeBinding
import com.example.movieapppractice.databinding.ActivityMainBinding
import com.example.movieapppractice.ui.adapter.HomePagerAdapter
import com.example.movieapppractice.ui.adapter.HomeSeriesAdapter
import com.example.movieapppractice.ui.base.BaseActivity
import com.example.movieapppractice.util.ext.observe
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {

    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var binding: ActivityHomeBinding
    private var type = 1

    private val adapter by lazy {
        HomePagerAdapter(::pagerClickListener)
        // 위와 같이 하면 가독성이 떨어지므로 아래와 같이 한다.
    }

    private val seriesAdapter1 by lazy {
        HomeSeriesAdapter(clicked = {seriesClickListener(it)})
    }
    private val seriesAdapter2 by lazy {
        HomeSeriesAdapter(clicked = {seriesClickListener(it)})
    }

    private fun pagerClickListener(item: HomePagerAdapter.PagerItem) {
        val fragment = MovieItemFragment.newInstance(item.movieId)
        supportFragmentManager.beginTransaction().add(R.id.container, fragment, "movie_item")
            .addToBackStack(null)
            .commit()
    }

    private fun seriesClickListener(item: HomeSeriesAdapter.SeriesItem) {
        val fragment = MovieItemFragment.newInstance(item.movieId)
        supportFragmentManager.beginTransaction().add(R.id.container, fragment, "movie_item")
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }


    override fun onResume() {
        super.onResume()
        viewModel.loadHome(type)
    }

    override fun observeChange() {
        observe(viewModel.pagerHomeLiveData, ::onDataLoaded)
    }

    private fun onDataLoaded(items: BaseResponse<MovieData>) {
        adapter.addItem(items.result)
        seriesAdapter1.addItem(items.result)
        seriesAdapter2.addItem(items.result)
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        with(binding) {
            viewPager.adapter = adapter

            root.context?.let {
                recyclerView1.layoutManager = LinearLayoutManager(it, LinearLayoutManager.HORIZONTAL, false)
                bannerTitle1.text = "정말 재밌는"
                recyclerView1.adapter = seriesAdapter1

                recyclerView2.layoutManager = LinearLayoutManager(it, LinearLayoutManager.HORIZONTAL, false)
                bannerTitle2.text = "한번 더 볼만한 작품"
                recyclerView2.adapter = seriesAdapter2
            }

        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                when (item.itemId) {
                    R.id.action_update -> getString(R.string.app_name)
                    else -> getString(R.string.app_name)
                }
            )
        )
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }
}