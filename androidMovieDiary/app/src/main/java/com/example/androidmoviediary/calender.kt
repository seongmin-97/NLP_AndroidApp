package com.example.androidmoviediary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_calender.*
import kotlinx.android.synthetic.main.fragment_calender.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection
import kotlin.concurrent.thread


class calender : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calender, container, false)

        // 클릭한 날짜를 저장하기 위한 클래스
        class reviewDate() {
            var year: Int = 0
            var month: Int = 0
            var day: Int = 0
        }

        var calenderDate = reviewDate()

        // 현재 날짜 표시하기
        val sdf = SimpleDateFormat("yyyy년 M월 d일")
        val currentDate = sdf.format(Date())
        view.date.text = "${currentDate}"

        // 앱을 첫 실행했을 때 날짜 클릭 안하고 바로 저장할 때 오늘 날짜로 저장 안되는 문제 해결
        var calendar:Calendar = Calendar.getInstance()
        calendar.time
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONDAY) + 1
        var day = calendar.get(Calendar.DATE)

        calenderDate.year = year
        calenderDate.month = month
        calenderDate.day = day

        // 날짜 클릭하면 입력창이 보이게하고, 텍스트 필드에 날짜 보여주기
        view.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date.text = "${year}년 ${month+1}월 ${dayOfMonth}일"

            calenderDate.year = year
            calenderDate.month = month+1
            calenderDate.day = dayOfMonth
        }

        // 검색 버튼 누르면 팝업창 띄우기

        view.searchTitle.setOnClickListener {
            val intent = Intent(activity, popUpActivity::class.java)
            val inputText = inputTitle.text.toString()
            intent.putExtra("title", inputText)
            Log.d("putExtra", "${inputText}")
            startActivityForResult(intent, 99)
        }

        // 저장 버튼 누르면 영화 리뷰 저장!
        val helper = SqliteHelper(activity, "review", 1)
        view.inputButton.setOnClickListener {
            val year = calenderDate.year
            val month = calenderDate.month
            val day = calenderDate.day
            val title = view.inputTitle.text.toString()
            val review = view.inputReview.text.toString()
            val rating = "좋음"
            val genre = "장르|영화장르"
            val movieYear = 2020
            var reviewData = Review(year, month, day, title, review, rating, genre, movieYear)

            if (helper.insertReviewedMovie(reviewData)) {
                // 메시지 출력
                val message = "리뷰가 저장되었습니다."
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            } else {
                val message = "제목과 리뷰를 입력해주세요."
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }

            // 제목, 리뷰 입력 칸 빈칸으로
            view.inputTitle.setText("")
            view.inputReview.setText("")
        }

        //  외부 터치시 키보드 내리기
        view.ConstraintLayout.setOnClickListener {
            it.hideKeyboard()
        }

        view.calendarView.setOnClickListener {
            it.hideKeyboard()
        }

        // 키보드 올라올 때 스크롤 끝까지
        view.inputTitle.setOnClickListener {
            view.scrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }

        view.inputReview.setOnClickListener {
            view.scrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val getTitle = data?.getStringExtra("returnTitle")
            Log.d("return", "${getTitle}")
            inputTitle.setText(getTitle)
        }
    }

    // 키보드 내리는 함수
    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(inputTitle.windowToken, 0)
    }
}