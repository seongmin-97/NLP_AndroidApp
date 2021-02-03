package com.example.androidmoviediary

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recommended_movies.*
import kotlinx.android.synthetic.main.fragment_recommended_movies.view.*
import kotlinx.android.synthetic.main.fragment_reviewed_movies.view.*
import kotlinx.android.synthetic.main.fragment_reviewed_movies.view.recyclerView
import kotlinx.android.synthetic.main.item_recycler_choose.*
import kotlinx.android.synthetic.main.item_recycler_choose.view.*

class recommendedMovies : Fragment() {
    // 가상 데이터 만드는 함수
    fun loadData(): MutableList<Movie> {
        val data:MutableList<Movie> = mutableListOf()

        for (no in 1..10) {
            val title = "아이언맨 ${no+1} : 에이지 오브 울트론"
            val rating = "별점 ${no+1} 점"
            val genre = "히어로 ${no+1}"
            val year = no+2000
            val plot = "뉴욕에서 음악 선생님으로 일하던 ‘조’는\n" +
                    "꿈에 그리던 최고의 밴드와 재즈 클럽에서 연주하게 된 그 날,\n" +
                    "예기치 못한 사고로 영혼이 되어 ‘태어나기 전 세상’에 떨어진다.\n" +
                    "\n" +
                    "탄생 전 영혼들이 멘토와 함께 자신의 관심사를 발견하면 지구 통행증을 발급하는 ‘태어나기 전 세상’\n" +
                    "‘조’는 그 곳에서 유일하게 지구에 가고 싶어하지 않는 시니컬한 영혼 ‘22’의 멘토가 된다.\n" +
                    "\n" +
                    "링컨, 간디, 테레사 수녀도 멘토되길 포기한 영혼 ‘22’\n" +
                    "꿈의 무대에 서려면 ‘22’의 지구 통행증이 필요한 ‘조’\n" +
                    "그는 다시 지구로 돌아가 꿈의 무대에 설 수 있을까?"
            var movie = Movie(title, rating, genre, year, plot)
            data.add(movie)
        }
        return data
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recommended_movies, container, false)

        // 리사이클러뷰
        val data:MutableList<Movie> = loadData()
        var adapter = CustomAdapter_choose()
        adapter.listData = data
        view.recyclerView.adapter = adapter
        view.recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }
}