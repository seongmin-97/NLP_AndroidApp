package com.example.androidmoviediary

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_pop_up.*
import kotlinx.android.synthetic.main.fragment_calender.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import kotlin.concurrent.thread

class popUpActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop_up)


        var adapter = customAdapter_Search()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(baseContext)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://99b4fcae2dd7.ap.ngrok.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val INPUTTITLE = intent.getStringExtra("title")
        Log.d("inputtitle", "${INPUTTITLE}")
        val useInterface = retrofit.create(searchMovies::class.java)

        useInterface.titles(INPUTTITLE).enqueue(object : Callback<List<movieInfoItem>> {
            override fun onFailure(call: Call<List<movieInfoItem>>, t: Throwable) {
                Log.d("getTitle", "error")
            }
            override fun onResponse(
                call: Call<List<movieInfoItem>>,
                response: Response<List<movieInfoItem>>
            ) {
                var titleList = response.body() as List<movieInfoItem>
                Log.d("getTitle", "${titleList}")
                adapter.listData.addAll(titleList.toMutableList())
                adapter.notifyDataSetChanged()
                button.visibility = View.VISIBLE
            }
        })
    }

    // 네트워크 연결을 위한 인터페이스
    interface searchMovies {
        @GET("api/movie/{title}")
        fun titles(@Path("title") title: String): Call<List<movieInfoItem>>
    }

    fun finishMe(returnIntent : Intent) {
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}