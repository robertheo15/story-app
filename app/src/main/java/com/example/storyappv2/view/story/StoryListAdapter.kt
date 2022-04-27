package com.example.storyappv2.view.story

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storyappv2.R
import com.example.storyappv2.databinding.ItemRowStoryBinding
import com.example.storyappv2.network.response.Story
import com.example.storyappv2.view.story.detail.DetailActivity
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class StoryListAdapter :
    PagingDataAdapter<Story, StoryListAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    private var stories: PagingData<Story>? = null

    fun setStoryList(stories: PagingData<Story>?) {
        this.stories = stories
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): StoryViewHolder {
        val view =
            ItemRowStoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class StoryViewHolder(private val binding: ItemRowStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(story: Story) {

            val zonedDateTime = ZonedDateTime.parse(story.createdAt)
            val dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("id", "ID"))

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.extraStory, story)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.imgItemPhoto, "storyImage"),
                        Pair(binding.tvName, "name"),
                        Pair(binding.tvDate, "date"),
                        Pair(binding.tvDescription, "description"),
                    )
                itemView.context.startActivity(intent, optionsCompat.toBundle())
            }

            binding.apply {
                Glide.with(itemView)
                    .load(story.photoUrl)
                    .placeholder(R.drawable.ic_block)
                    .into(imgItemPhoto)
                tvName.text = story.name
                tvDate.text = dtf.format(zonedDateTime)
                tvDescription.text = story.description
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}