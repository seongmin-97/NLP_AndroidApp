package com.example.androidmoviediary

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_calender.*
import kotlinx.android.synthetic.main.fragment_calender.view.*
import java.text.SimpleDateFormat
import java.util.*


class calender : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calender, container, false)

        // 현재 날짜 표시하기
        val sdf = SimpleDateFormat("yyyy년 M월 d일")
        val currentDate = sdf.format(Date())
        view.date.text = "${currentDate}"

        // 클릭한 날짜를 저장하기 위한 클래스
        class reviewDate() {
            var year: Int = 0
            var month: Int = 0
            var day: Int = 0
        }

        var calenderDate = reviewDate()

        // 날짜 클릭하면 입력창이 보이게하고, 텍스트 필드에 날짜 보여주기
        view.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date.text = "${year}년 ${month+1}월 ${dayOfMonth}일"

            calenderDate.year = year
            calenderDate.month = month+1
            calenderDate.day = dayOfMonth
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
                val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
                val view = toast.view
                view.setBackgroundColor(resources.getColor(R.color.black))

                val group = toast.view as ViewGroup
                val msgTextView = group.getChildAt(0) as TextView
                msgTextView.setTextColor(resources.getColor(R.color.white))
                toast.show()
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

        // 키보드 올라오면 전체적인 레이아웃 올리기

        view.date.setOnClickListener {
            Log.d("scroll", "it is done")
        }
        return view
    }

    // 키보드 내리는 함수
    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(inputTitle.windowToken, 0)
    }

    // 스크롤 끝까지 올리는 함수
    fun View.scroll() {
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }
        Log.d("scroll", "it is done")
    }
}