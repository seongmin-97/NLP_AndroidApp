package com.example.androidmoviediary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_pop_up.*
import kotlinx.android.synthetic.main.fragment_calender.view.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class popUpActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up)
        // 영화 데이터 불러와서 다 보여주자
        thread(start=true) {

            // 영화 데이터를 리사이클러뷰로 보여줌
            var adapter = customAdapter_Search()

            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(baseContext)

            // 레트로핏2 이용
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://7cc73acea3da.ap.ngrok.io")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val INPUTTITLE = intent.getStringExtra("title")
            Log.d("inputtitle", "${INPUTTITLE}")
            val useInterface = retrofit.create(MainActivity.searchMovies::class.java)

            useInterface.titles(INPUTTITLE).enqueue(object : Callback<List<movieInfoItem>> {

                // 네트워크가 불안정할 때
                override fun onFailure(call: Call<List<movieInfoItem>>, t: Throwable) {
                    val message = "네트워크가 원할하지 않습니다."
                    Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
                    finish()
                }

                // 네트워크가 잘 될때
                override fun onResponse(
                        call: Call<List<movieInfoItem>>,
                        responsePop: Response<List<movieInfoItem>>
                ) {
                    var titlePopup = responsePop.body() as List<movieInfoItem>
                    Log.d("getTitle", "${titlePopup.size}")
                    adapter.listData.addAll(titlePopup.toMutableList())
                    adapter.notifyDataSetChanged()
                    button.visibility = View.VISIBLE
                }
            })
        }

        // 원하는 영화가 없으면 아무일 없이 다시 돌아가자.
        button.setOnClickListener {
            val intent = Intent()
            intent.putExtra("returnTitle", "clickedButton")
            finishMe(intent)
        }
    }


    // 네트워크 연결을 위한 인터페이스
    interface searchMovies {
        @GET("api/movie/{title}")
        fun titles(@Path("title") title: String): Call<List<movieInfoItem>>
    }

    // 아이템뷰를 클릭하면 액티비티가 끝나면서 값을 전달할 수 있게 여기서 함수로 구현
    fun finishMe(returnIntent : Intent) {
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}