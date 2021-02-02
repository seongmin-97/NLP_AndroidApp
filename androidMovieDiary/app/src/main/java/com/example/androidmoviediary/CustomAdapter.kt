package com.example.androidmoviediary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*

class CustomAdapter : RecyclerView.Adapter<Holder>() {
    var listData = mutableListOf<Review>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val review = listData.get(position)
        holder.setMovie(review)
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setMovie(review: Review) {
        itemView.movieTitle.text = "${review.title}"
        itemView.movieRating.text = "${review.rating}"
        itemView.movieGenre.text = "${review.genre}"
        itemView.movieYear.text = "${review.year}"
        itemView.moviePlot.text = "${review.review}"
    }
}