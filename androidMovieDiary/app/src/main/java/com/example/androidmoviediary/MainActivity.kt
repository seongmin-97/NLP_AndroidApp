package com.example.androidmoviediary

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import java.security.AccessController.getContext


class MainActivity : AppCompatActivity() {
    var imm : InputMethodManager? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 뷰페이저와 프래그먼트 연결
        val fragmentList = listOf(calender(), reviewedMovies(), recommendedMovies(), moviesToWatchLater(), settings())
        val adapter = FragmentAdapter(this)
        adapter.fragmentList = fragmentList
        viewPager.adapter = adapter

        // 탭 레이아웃과 연결 icon 출처: flacicon
        val icon = mutableListOf(R.drawable.calendaricon, R.drawable.reviewicon, R.drawable.recommendicon, R.drawable.listicon, R.drawable.settingsicon)

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.setIcon(icon[position])
        }.attach()

        // 뷰페이저 전환되면 페이지 내리기
        viewPager.setPageTransformer { page, position ->
            page.hideKeyboard()
        }
    }

    // 키보드 내리는 함수
    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}