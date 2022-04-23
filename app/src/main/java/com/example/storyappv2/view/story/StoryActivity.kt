package com.example.storyappv2.view.story

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyappv2.R
import com.example.storyappv2.databinding.ActivityStoryBinding
import com.example.storyappv2.network.User
import com.example.storyappv2.utils.UserPreference
import com.example.storyappv2.utils.ViewModelFactory
import com.example.storyappv2.view.map.MapsActivity
import com.example.storyappv2.view.login.LoginActivity
import com.example.storyappv2.view.story.create.CreateActivity
import com.example.storyappv2.view.welcome.WelcomeActivity

class StoryActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var storyViewModel: StoryViewModel
    private lateinit var binding: ActivityStoryBinding
    private lateinit var adapter: StoryListAdapter
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()
        setupViewModel()

    }

    private fun setupViewModel() {
        val pref = UserPreference.getInstance(dataStore)

        storyViewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref, this)
        )[StoryViewModel::class.java]

        storyViewModel.getUser().observe(this) { user ->
            this.user = user
            if (!user.isLogin) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                storyViewModel.story(user.token).observe(this) {
                    adapter.setStoryList(it)
                    adapter.submitData(lifecycle, it)
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
                startActivity(Intent(this, MapsActivity::class.java))
                true
            }
            R.id.addStory -> {
                startActivity(Intent(this, CreateActivity::class.java))
                true
            }
            R.id.setting -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.logout -> {
                storyViewModel.logout()
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
                true
            }
            else -> true
        }
    }
}