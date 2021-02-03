package com.example.androidmoviediary

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_calender.*
import kotlinx.android.synthetic.main.fragment_reviewed_movies.*
import kotlinx.android.synthetic.main.fragment_reviewed_movies.view.*


class reviewedMovies : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reviewed_movies, container, false)

        var adapter = CustomAdapter()
        val helper = SqliteHelper(activity, "review", 1)
        adapter.listData.addAll(helper.selectReviewedMovie())
        view.recyclerView.adapter = adapter
        view.recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onResume() {
        super.onResume()

        var adapter = CustomAdapter()
        val helper = SqliteHelper(activity, "review", 1)
        adapter.listData.addAll(helper.selectReviewedMovie())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }
}