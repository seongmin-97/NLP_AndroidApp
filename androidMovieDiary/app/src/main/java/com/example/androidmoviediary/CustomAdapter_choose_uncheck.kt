package com.example.androidmoviediary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.movieGenre
import kotlinx.android.synthetic.main.item_recycler.view.moviePlot
import kotlinx.android.synthetic.main.item_recycler.view.movieRating
import kotlinx.android.synthetic.main.item_recycler.view.movieTitle
import kotlinx.android.synthetic.main.item_recycler.view.movieYear
import kotlinx.android.synthetic.main.item_recycler_choose.view.*

class CustomAdapter_choose_uncheck : RecyclerView.Adapter<CustomAdapter_choose_uncheck.Holder_choose_uncheck>() {
    var listData = mutableListOf<Movie>()

    inner class Holder_choose_uncheck(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var helper = SqliteHelper(itemView.getContext(), "movie", 1)

        init {
            itemView.checkButton.setOnClickListener {
                val title = itemView.movieTitle.text.toString()
                val rating =  "${itemView.movieRating.text.toString()}"
                val genre = itemView.movieGenre.text.toString()
                val year = itemView.movieYear.text.toString().toInt()
                val plot = itemView.moviePlot.text.toString()
                var movie = Movie(title, rating, genre, year, plot)
                helper.deleteRecommendedMovie(movie)
                listData.remove(movie)
                notifyDataSetChanged()
            }
        }
        fun setMovie(movie: Movie) {
            itemView.movieTitle.text = "${movie.title}"
            itemView.movieRating.text = "${movie.rating}"
            itemView.movieGenre.text = "${movie.genre}"
            itemView.movieYear.text = "${movie.year}"
            itemView.moviePlot.text = "${movie.plot}"
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

