package com.example.androidmoviediary

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap
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
import kotlinx.android.synthetic.main.activity_pop_up.*
import kotlinx.android.synthetic.main.fragment_calender.*
import kotlinx.android.synthetic.main.fragment_calender.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
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
            if (inputText.isEmpty()) {
                val message = "제목을 입력해주세요."
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            } else {
                intent.putExtra("title", inputText)
                Log.d("putExtra", "${inputText}")
                startActivityForResult(intent, 99)
            }
        }

        // 저장 버튼 누르면 영화 리뷰 저장!
        val helper = SqliteHelper(activity, "review", 1)
        view.inputButton.setOnClickListener {
            // 웸의 데이터에서 해당 영화가 있으면 가져오고 없으면 입력한대로 출력
                val message = "리뷰를 저장하는 중입니다."
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                thread(start=true) {
                    val retrofit = Retrofit.Builder()
                            .baseUrl("http://nlpandroidapp.pythonanywhere.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                    val INPUTTITLE = view.inputTitle.text.toString()
                    Log.d("inputtitle", "${INPUTTITLE}")
                    val useInterface = retrofit.create(getMovies::class.java)

                    useInterface.titles(INPUTTITLE).enqueue(object : Callback<List<movieInfoItem>> {
                        // 네트워크가 통신이 안되면
                        override fun onFailure(call: Call<List<movieInfoItem>>, t: Throwable) {
                            val message = "네트워크가 원할하지 않습니다."
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                                call: Call<List<movieInfoItem>>,
                                response: Response<List<movieInfoItem>>
                        ) {
                            var titleList = response.body() as List<movieInfoItem>
                            Log.d("getTitle", "${titleList.size}")

                            thread(start=true) {
                                val retrofit = Retrofit.Builder()
                                        .baseUrl("http://nlpandroidapp.pythonanywhere.com/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build()
                                val INPUTQUERY = view.inputReview.text.toString()
                                Log.d("inputtitle", "${INPUTTITLE}")
                                val useInterface = retrofit.create(getRating::class.java)

                                useInterface.ratings(INPUTQUERY).enqueue(object : Callback<List<getRatingItem>> {
                                    override fun onFailure(call: Call<List<getRatingItem>>, t: Throwable) {
                                        val message = "네트워크가 원할하지 않습니다."
                                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                    }

                                    override fun onResponse(call: Call<List<getRatingItem>>, response: Response<List<getRatingItem>>) {
                                        var output = response.body() as List<getRatingItem>
                                        val rating = output.get(0).rating.toString()
                                        if (titleList.size == 0) {
                                            val year = calenderDate.year
                                            val month = calenderDate.month
                                            val day = calenderDate.day
                                            val title = view.inputTitle.text.toString()
                                            val review = view.inputReview.text.toString()
                                            val genre = ""
                                            val movieYear = ""
                                            val img_url = "https://ssl.pstatic.net/static/movie/2011/06/poster_default.gif"
                                            var reviewData = Review(year, month, day, title, review, rating, genre, movieYear, img_url)

                                            if (helper.insertReviewedMovie(reviewData)) {
                                                // 메시지 출력
                                                val message = "리뷰가 저장되었습니다."
                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()

                                                // 제목, 리뷰 입력 칸 빈칸으로
                                                view.inputTitle.setText("")
                                                view.inputReview.setText("")
                                            } else {
                                                val message = "제목과 리뷰를 입력해주세요."
                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                            }
                                        } else {
                                            val year = calenderDate.year
                                            val month = calenderDate.month
                                            val day = calenderDate.day
                                            val title = titleList.get(0).title
                                            val review = view.inputReview.text.toString()
                                            val genre = titleList.get(0).genre
                                            val movieYear = titleList.get(0).year.toString()
                                            val img_url = titleList.get(0).img_url
                                            var reviewData = Review(year, month, day, title, review, rating, genre, movieYear, img_url)

                                            if (helper.insertReviewedMovie(reviewData)) {
                                                // 메시지 출력
                                                val message = "리뷰가 저장되었습니다."
                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()

                                                // 제목, 리뷰 입력 칸 빈칸으로
                                                view.inputTitle.setText("")
                                                view.inputReview.setText("")
                                            } else {
                                                val message = "제목과 리뷰를 입력해주세요."
                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    }
                                })
                            }
                        }
                    })
                }

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

    // 팝업 창에서 입력한 결과 리턴
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val getTitle = data?.getStringExtra("returnTitle")
            Log.d("return", "${getTitle}")
            if (getTitle == "clickedButton") {
                inputTitle.setSelection(inputTitle.text.length)
            } else {
                inputTitle.setText(getTitle)
                inputTitle.setSelection(inputTitle.text.length)
            }
        }
    }

    // 키보드 내리는 함수
    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(inputTitle.windowToken, 0)
    }

    // 네트워크 연결을 위한 인터페이스
    interface getMovies {
        @GET("api/movieInfo/{title}")
        fun titles(@Path("title") title: String): Call<List<movieInfoItem>>
    }

    interface getRating {
        @GET("api/predict")
        fun ratings(@Query("review")query:String): Call<List<getRatingItem>>
    }
}