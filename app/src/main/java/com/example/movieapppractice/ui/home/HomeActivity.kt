package com.example.movieapppractice.ui.home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapppractice.R
import com.example.movieapppractice.data.model.MovieHome
import com.example.movieapppractice.databinding.ActivityHomeBinding
import com.example.movieapppractice.ui.adapter.HomePagerAdapter
import com.example.movieapppractice.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {

    private val viewModel by viewModel<HomeViewModel>()
    private lateinit var binding: ActivityHomeBinding
    private val adapter by lazy {
        HomePagerAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHome()
    }

    override fun observeChange() {
        viewModel.pagerHomeLiveData.observe(this, Observer {
            it?.let {
                onDataLoaded(it.result)
            }
        })
    }

    private fun onDataLoaded(items: ArrayList<MovieHome.MovieData>) {
        adapter.addItem(items)
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)

        with(binding) {
            viewPager.adapter = adapter
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