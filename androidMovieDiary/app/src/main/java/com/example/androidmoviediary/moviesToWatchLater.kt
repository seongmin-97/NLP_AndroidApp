package com.example.androidmoviediary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movies_to_watch_later.*
import kotlinx.android.synthetic.main.fragment_movies_to_watch_later.view.*
import kotlinx.android.synthetic.main.fragment_reviewed_movies.view.*
import kotlinx.android.synthetic.main.fragment_reviewed_movies.view.recyclerView
import kotlinx.android.synthetic.main.item_recycler_choose.*
import kotlinx.android.synthetic.main.item_recycler_choose.view.*


class moviesToWatchLater : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movies_to_watch_later, container, false)

        var adapter = CustomAdapter_choose_uncheck()
        val helper = SqliteHelper(activity, "movie", 1)
        adapter.listData.addAll(helper.selectRecommendedMovie())
        view.recyclerView.adapter = adapter
        view.recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onResume() {
        super.onResume()

        var adapter = CustomAdapter_choose_uncheck()
        val helper = SqliteHelper(activity, "movie", 1)
        adapter.listData.addAll(helper.selectRecommendedMovie())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
}