package com.droid.newsapiclient.presentation.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.droid.newsapiclient.data.model.Article
import com.droid.newsapiclient.data.util.extensions.convertToHoursMins
import com.droid.newsapiclient.databinding.NewsItemLayoutBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(
        val binding: NewsItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {

                textView8.text = article.title
                textView6.text = article.publishedAt?.let { convertToHoursMins(it) }
                textView7.text = article.source?.name

                val transformation = MultiTransformation(CenterCrop(), RoundedCorners(15))
                Glide.with(binding.imageView3.context).load(article.urlToImage)
                    .transform(transformation).into(binding.imageView3)

                binding.root.setOnClickListener {
                    onItemClickListener?.let { it(article) }
                }
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = NewsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }


    private val diffUtil = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)
    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

}








