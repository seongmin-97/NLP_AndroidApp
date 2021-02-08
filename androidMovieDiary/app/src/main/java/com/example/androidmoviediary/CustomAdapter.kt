package com.example.androidmoviediary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_recycler.view.*

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.Holder>() {
    var listData = mutableListOf<Review>()

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val helper = SqliteHelper(itemView.getContext(), "review", 1)

        // 버튼 누르면 리뷰했던 영화에서 삭제
        init {
            itemView.uncheckButton.setOnClickListener {
                val year = itemView.yearText.text.toString().toInt()
                val month = itemView.monthText.text.toString().toInt()
                val day = itemView.dayText.text.toString().toInt()
                val title = itemView.movieTitle.text.toString()
                val review = itemView.movieReview.text.toString()
                val rating = itemView.movieRating.text.toString()
                val genre = itemView.movieGenre.text.toString()
                val movieYear = itemView.movieYear.text.toString()
                val img_url = itemView.img_url.text.toString()
                var reviewData = Review(year, month, day, title, review, rating, genre, movieYear, img_url)
                helper.deleteReviewedMovie(reviewData)
                listData.remove(reviewData)
                notifyDataSetChanged()

                // 메세지 울력
                val message = "리뷰가 삭제되었습니다."
                Toast.makeText(itemView.getContext(), message, Toast.LENGTH_LONG).show()
            }
        }
        // 아이템 뷰에 데이터 집어넣기
        fun setMovie(review: Review) {
            itemView.yearText.text = "${review.year}"
            itemView.monthText.text = "${review.month}"
            itemView.dayText.text = "${review.day}"
            itemView.movieTitle.text = "${review.title}"
            itemView.movieRating.text = "${review.rating}"
            itemView.movieGenre.text = "${review.genre}"
            itemView.movieYear.text = "${review.movieYear}"
            itemView.movieReview.text = "${review.review}"
            itemView.img_url.text = "${review.img_url}"
            Glide.with(itemView).load(review.img_url).into(itemView.imageView)
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