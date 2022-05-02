package com.seungho.android.myapplication.movieinformationex.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seungho.android.myapplication.movieinformationex.DetailMovieActivity
import com.seungho.android.myapplication.movieinformationex.R
import com.seungho.android.myapplication.movieinformationex.databinding.ItemMovieBinding
import com.seungho.android.myapplication.movieinformationex.data.retrofit.MovieModel
import com.seungho.android.myapplication.movieinformationex.util.GlideApp

class PosterAdapter(private val listData: List<MovieModel>, private val context: Context): RecyclerView.Adapter<PosterAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setNews(listData[position],context)
    }

    override fun getItemCount(): Int {
        return listData.count()
    }

    inner class Holder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setNews(item: MovieModel, context: Context) {
            GlideApp
                .with(context)
                .load(item.poster)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.posterImageView)

            binding.cardView.setOnClickListener {
                val intent = Intent(context, DetailMovieActivity::class.java)
                intent.putExtra("MovieModel", item)
                ContextCompat.startActivity(context, intent, null)
            }
        }
    }
}