package com.example.androidmoviediary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_recycler.view.movieGenre
import kotlinx.android.synthetic.main.item_recycler.view.movieReview
import kotlinx.android.synthetic.main.item_recycler.view.movieRating
import kotlinx.android.synthetic.main.item_recycler.view.movieTitle
import kotlinx.android.synthetic.main.item_recycler.view.movieYear
import kotlinx.android.synthetic.main.item_recycler_choose_uncheck.view.*

class CustomAdapter_choose_uncheck : RecyclerView.Adapter<CustomAdapter_choose_uncheck.Holder_choose_uncheck>() {
    var listData = mutableListOf<Movie>()

    inner class Holder_choose_uncheck(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var helper = SqliteHelper(itemView.getContext(), "movie", 1)

        // 버튼 누르면 나중에 볼 영화에서 삭제
        init {
            itemView.checkButton.setOnClickListener {
                val title = itemView.movieTitle.text.toString()
                val rating =  "${itemView.movieRating.text.toString()}"
                val genre = itemView.movieGenre.text.toString()
                val year = itemView.movieYear.text.toString().toInt()
                val plot = itemView.movieReview.text.toString()
                val img_url = itemView.imgUrl.text.toString()
                var movie = Movie(title, rating, genre, year, plot, img_url)
                helper.deleteRecommendedMovie(movie)
                listData.remove(movie)
                notifyDataSetChanged()

                // 메세지 울력
                val message = "나중에 볼 영화에서 삭제되었습니다."
                Toast.makeText(itemView.getContext(), message, Toast.LENGTH_LONG).show()
            }
        }

        // 아이템 뷰에 데이터 집어넣기
        fun setMovie(movie: Movie) {
            itemView.movieTitle.text = "${movie.title}"
            itemView.movieRating.text = "${movie.rating}"
            itemView.movieGenre.text = "${movie.genre}"
            itemView.movieYear.text = "${movie.year}"
            itemView.movieReview.text = "${movie.plot}"
            itemView.imgUrl.text = "${movie.img_url}"
            Glide.with(itemView).load(movie.img_url).into(itemView.imageView)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder_choose_uncheck {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_choose_uncheck, parent, false)
        return Holder_choose_uncheck(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder_choose_uncheck, position: Int) {
        val movie = listData.get(position)
        holder.setMovie(movie)
    }
}

