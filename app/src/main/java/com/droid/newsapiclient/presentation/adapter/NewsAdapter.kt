package com.droid.newsapiclient.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droid.newsapiclient.data.model.Article
import com.droid.newsapiclient.databinding.NewsListItemBinding

class NewsAdapter : ListAdapter<Article,NewsAdapter.NewsViewHolder>(diffUtil) {

    inner class NewsViewHolder(
        val binding: NewsListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                tvTitle.text = article.title
                tvDescription.text = article.description
                tvPublishedAt.text = article.publishedAt
                tvSource.text = article.source.name
//                Glide.with(ivArticleImage.context)
//                    .load(article.urlToImage)
//                    .into(ivArticleImage)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = NewsListItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }

}


val diffUtil = object : DiffUtil.ItemCallback<Article>(){
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return  oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.content == newItem.content

    }

}
