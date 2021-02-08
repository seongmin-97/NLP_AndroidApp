package com.example.androidmoviediary

import android.app.Activity
import android.content.Intent
import android.telecom.Call
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_calender.*
import kotlinx.android.synthetic.main.item_search_movie.view.*

class customAdapter_Search() : RecyclerView.Adapter<customAdapter_Search.Holder_Search>() {
    var listData = mutableListOf<movieInfoItem>()

    inner class Holder_Search(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                Log.d("itemView", "clicked")
                var title = itemView.movieTitle.text.toString()
                var intent = Intent(itemView.context, MainActivity::class.java)
                intent.putExtra("returnTitle", title)
                val activity : popUpActivity = itemView.context as popUpActivity
                activity.finishMe(intent)
            }
        }

        fun setResult(result: movieInfoItem) {
            itemView.movieTitle.text = "${result.title}"
            itemView.movieRating.text = "${result.rating}"
            itemView.movieGenre.text = "${result.genre}"
            itemView.movieYear.text = "${result.year}"
            Glide.with(itemView).load(result.img_url).into(itemView.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder_Search {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_movie, parent, false)
        return Holder_Search(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder_Search, position: Int) {
        val result = listData.get(position)
        holder.setResult(result)
    }
}
