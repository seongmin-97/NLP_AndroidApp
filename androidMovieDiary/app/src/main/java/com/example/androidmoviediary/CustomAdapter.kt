package com.example.androidmoviediary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.Holder>() {
    var listData = mutableListOf<Review>()

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val helper = SqliteHelper(itemView.getContext(), "review", 1)

        // 버튼 누르면 리뷰했떤 영화에서 삭제
        init {
            itemView.uncheckButton.setOnClickListener {
                val year = itemView.yearText.text.toString().toInt()
                val month = itemView.monthText.text.toString().toInt()
                val day = itemView.dayText.text.toString().toInt()
                val title = itemView.movieTitle.text.toString()
                val review = itemView.movieReview.text.toString()
                val rating = itemView.movieRating.text.toString()
                val genre = itemView.movieGenre.text.toString()
                val movieYear = itemView.movieYear.text.toString().toInt()
                var reviewData = Review(year, month, day, title, review, rating, genre, movieYear)
                helper.deleteReviewedMovie(reviewData)
                listData.remove(reviewData)
                notifyDataSetChanged()
            }
        }

        fun setMovie(review: Review) {
            itemView.yearText.text = "${review.year}"
            itemView.monthText.text = "${review.month}"
            itemView.dayText.text = "${review.day}"
            itemView.movieTitle.text = "${review.title}"
            itemView.movieRating.text = "${review.rating}"
            itemView.movieGenre.text = "${review.genre}"
            itemView.movieYear.text = "${review.movieYear}"
            itemView.movieReview.text = "${review.review}"
        }
    }

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