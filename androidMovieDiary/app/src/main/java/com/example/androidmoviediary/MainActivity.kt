package com.example.androidmoviediary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 뷰페이저와 프래그먼트 연결
        val fragmentList = listOf(calender(), reviewedMovies(), recommendedMovies(), moviesToWatchLater(), settings())
        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList
        viewPager.adapter = adapter

        // 탭 레이아웃과 연결
        val tabTitles = listOf<String>("달력", "봤던 영화", "추천 영화", "찜한 영화", "설정")
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }
}