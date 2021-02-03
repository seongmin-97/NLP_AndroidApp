package com.example.androidmoviediary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.movieGenre
import kotlinx.android.synthetic.main.item_recycler.view.movieReview
import kotlinx.android.synthetic.main.item_recycler.view.movieRating
import kotlinx.android.synthetic.main.item_recycler.view.movieTitle
import kotlinx.android.synthetic.main.item_recycler.view.movieYear
import kotlinx.android.synthetic.main.item_recycler_choose.view.*

class CustomAdapter_choose : RecyclerView.Adapter<CustomAdapter_choose.Holder_choose>() {
    var listData = mutableListOf<Movie>()

    inner class Holder_choose(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // 아이템 뷰에 데이터 집어넣기
        fun setMovie(movie: Movie) {
            itemView.movieTitle.text = "${movie.title}"
            itemView.movieRating.text = "${movie.rating}"
            itemView.movieGenre.text = "${movie.genre}"
            itemView.movieYear.text = "${movie.year}"
            itemView.movieReview.text = "${movie.plot}"
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder_choose {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_choose, parent, false)

        return Holder_choose(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder_choose, position: Int) {
        val movie = listData.get(position)
        holder.setMovie(movie)

        // 체크 버튼 누르면 나중에 볼 영화 목록에 추가
        val helper = SqliteHelper(holder.itemView.getContext(), "movie", 1)
        holder.itemView.checkButton.setOnClickListener{
            val title = holder.itemView.movieTitle.text.toString()
            val rating =  "${holder.itemView.movieRating.text.toString()}"
            val genre = holder.itemView.movieGenre.text.toString()
            val year = holder.itemView.movieYear.text.toString().toInt()
            val plot = holder.itemView.movieReview.text.toString()
            var movie = Movie(title, rating, genre, year, plot)
            helper.insertRecommendedMovie(movie)

            // 메세지 울력
            val message = "나중에 볼 영화에 추가되었습니다."
            Toast.makeText(holder.itemView.getContext(), message, Toast.LENGTH_LONG).show()
        }
    }
}

