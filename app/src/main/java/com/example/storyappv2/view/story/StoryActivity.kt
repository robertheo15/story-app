package com.example.storyappv2.view.story

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyappv2.R
import com.example.storyappv2.databinding.ActivityStoryBinding
import com.example.storyappv2.view.map.MapsActivity
import com.example.storyappv2.view.login.LoginActivity
import com.example.storyappv2.view.login.LoginViewModel
import com.example.storyappv2.view.story.create.CreateActivity
import com.example.storyappv2.view.welcome.WelcomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoryActivity : AppCompatActivity() {

    private val storyViewModel: StoryViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityStoryBinding
    private lateinit var adapter: StoryListAdapter
    private var job: Job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
        setupViewModel()

    }

    private fun setupViewModel() {

        lifecycleScope.launchWhenResumed {
            if (job.isActive) job.cancel()
            loginViewModel.getUser().collect { user ->
                if (user?.isLogin == false) {
                    startActivity(Intent(this@StoryActivity, LoginActivity::class.java))
                    finish()
                } else {
                    storyViewModel.findAllStories(user?.token!!)
                        .observe(this@StoryActivity) { stories ->
                            adapter.setStoryList(stories)
                            adapter.submitData(lifecycle, stories)
                        }

                }
            }
        }
    }

    private fun setRecyclerView() {
        binding.recyclerViewHome.layoutManager = LinearLayoutManager(this)
        adapter = StoryListAdapter()
        binding.recyclerViewHome.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.map -> {
                lifecycleScope.launchWhenResumed {
                    if (job.isActive) job.cancel()
                    job = launch {
                        startActivity(Intent(this@StoryActivity, MapsActivity::class.java))
                    }
                }
                true
            }
            R.id.addStory -> {
                lifecycleScope.launchWhenResumed {
                    if (job.isActive) job.cancel()
                    job = launch {
                        startActivity(Intent(this@StoryActivity, CreateActivity::class.java))
                    }
                }
                true
            }
            R.id.setting -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.logout -> {
                lifecycleScope.launchWhenResumed {
                    if (job.isActive) job.cancel()
                    job = launch {
                        loginViewModel.logout()
                        startActivity(Intent(this@StoryActivity, WelcomeActivity::class.java))
                        finish()
                    }
                }
                true
            }
            else -> true
        }
    }
}