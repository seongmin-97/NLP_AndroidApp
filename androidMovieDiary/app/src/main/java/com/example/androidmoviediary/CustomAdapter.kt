package com.example.androidmoviediary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*

class CustomAdapter : RecyclerView.Adapter<Holder>() {
    var listData = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val movie = listData.get(position)
        holder.setMovie(movie)
    }
}

class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setMovie(movie: Movie) {
        itemView.movieTitle.text = "${movie.title}"
        itemView.movieRating.text = "${movie.rating}"
        itemView.movieGenre.text = "${movie.genre}"
        itemView.movieYear.text = "${movie.year}"
        itemView.moviePlot.text = "${movie.plot}"
    }
}