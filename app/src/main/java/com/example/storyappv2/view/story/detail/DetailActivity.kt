package com.example.storyappv2.view.story.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.storyappv2.R
import com.example.storyappv2.databinding.ActivityDetailBinding
import com.example.storyappv2.network.response.Story
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val story = intent.getParcelableExtra<Story>(extraStory) as Story
        val zonedDateTime = ZonedDateTime.parse(story.createdAt)
        val dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("id", "ID"))

        Glide.with(this)
            .load(story.photoUrl)
            .placeholder(R.drawable.ic_block)
            .into(binding.imgItemPhoto)
        binding.tvName.text = story.name
        binding.tvDate.text = dtf.format(zonedDateTime)
        binding.tvDescription.text = story.description
    }

    companion object {
        const val extraStory = "userDetail"
    }
}