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

        // 어댑터 연결
        var adapter = CustomAdapter()
        val helper = SqliteHelper(activity, "review", 1)
        adapter.listData.addAll(helper.selectReviewedMovie())
        view.recyclerView.adapter = adapter

        // 역순으로 정렬
        val manager = LinearLayoutManager(context)
        manager.reverseLayout = true
        manager.stackFromEnd = true
        view.recyclerView.layoutManager = manager
        return view
    }

    // 실시간으로 업데이터되게
    override fun onResume() {
        super.onResume()

        // 어댑터 연결
        var adapter = CustomAdapter()
        val helper = SqliteHelper(activity, "review", 1)
        adapter.listData.addAll(helper.selectReviewedMovie())
        recyclerView.adapter = adapter

        // 역순으로 정렬
        val manager = LinearLayoutManager(context)
        manager.reverseLayout = true
        manager.stackFromEnd = true
        recyclerView.layoutManager = manager
    }
}