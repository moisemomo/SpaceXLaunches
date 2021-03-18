package com.example.kmmapp.androidApp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kmmapp.androidApp.databinding.ActivityMainBinding
import com.example.kmmapp.shared.SpaceXSDK
import com.example.kmmapp.shared.cache.DatabaseDriverFactory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val launchesRvAdapter = LaunchesRvAdapter(listOf())
    private val sdk = SpaceXSDK(DatabaseDriverFactory(this))
    private val mainScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupSwipeRefresh()
        displayLaunches(false)
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            displayLaunches(true)
        }
    }

    private fun setupRecyclerView() {
        binding.launchesRv.apply {
            adapter = launchesRvAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun displayLaunches(needReload: Boolean) {
        binding.progress.visibility = View.VISIBLE
        mainScope.launch {
            kotlin.runCatching { sdk.getLaunches(needReload) }
                .onSuccess {
                    launchesRvAdapter.apply {
                        launches = it
                        notifyDataSetChanged()
                    }
                }
                .onFailure {
                    Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            binding.progress.visibility = View.GONE

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}
