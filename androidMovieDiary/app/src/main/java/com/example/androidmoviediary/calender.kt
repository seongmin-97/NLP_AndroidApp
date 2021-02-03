package com.example.androidmoviediary

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.androidmoviediary.inputReview
import kotlinx.android.synthetic.main.fragment_calender.*
import kotlinx.android.synthetic.main.fragment_calender.view.*
import kotlinx.android.synthetic.main.fragment_input_review.*
import kotlinx.android.synthetic.main.fragment_input_review.view.*
import java.util.*


class calender : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity!!.window
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

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

        // 날짜 클릭하면 입력창이 보이게하고, 텍스트 필드에 날짜 보여주기
        view.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date.visibility = View.VISIBLE
            inputTitle.visibility = View.VISIBLE
            inputReview.visibility = View.VISIBLE
            inputButton.visibility = View.VISIBLE

            date.text = "${month+1}월 ${dayOfMonth}일"

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
            helper.insertReviewedMovie(reviewData)

            view.inputTitle.setText("")
            view.inputReview.setText("")
        }

        //  외부 터치시 키보드 내리기
        view.setOnClickListener {
            it.hideKeyboard()
        }
        activity!!.window
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        return view
    }

    // 키보드 내리는 함수
    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}