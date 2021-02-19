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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import kotlin.concurrent.thread

class recommendedMovies : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recommended_movies, container, false)

        // 최근 리뷰한 영화 보여주기
        thread(start=true) {
            val helper = SqliteHelper(activity, "review", 1)
            Log.d("select", "${helper.selectReviewedMovieReverce()}  1111")
            // 추천 영화 restAPI로 검색하기
            if (helper.selectReviewedMovieReverce().size != 0) {
                val retrofit = Retrofit.Builder()
                        .baseUrl("https://434063da14e9.ap.ngrok.io")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                val useInterface = retrofit.create(searchRecommend::class.java)
                Log.d("qwertyqwrety", "${helper.selectReviewedMovieReverce().get(0)}")
                useInterface.titles(helper.selectReviewedMovieReverce().get(0).title.toString()).enqueue(object : Callback<List<getRecommendItem>> {
                    // 네트워크가 불안정할 때
                    override fun onFailure(call: Call<List<getRecommendItem>>, t: Throwable) {
                        val message = "네트워크가 원할하지 않습니다."
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    }

                    // 네트워크가 잘 될 때
                    override fun onResponse(call: Call<List<getRecommendItem>>, response: Response<List<getRecommendItem>>) {
                        // 우선 추천 목록을 받아서
                        var titleList = response.body() as List<getRecommendItem>
                        var recommendList = titleList.get(0)

                        // 추천 영화 제목들을 리스트로 저장 (밑에서 반복문으로 활용)
                        var result = mutableListOf<String>()
                        result.add(recommendList.movie_1st)
                        result.add(recommendList.movie_2nd)
                        result.add(recommendList.movie_3rd)
                        result.add(recommendList.movie_4th)
                        result.add(recommendList.movie_5th)
                        result.add(recommendList.movie_6th)
                        result.add(recommendList.movie_7th)
                        result.add(recommendList.movie_8th)
                        result.add(recommendList.movie_9th)
                        result.add(recommendList.movie_10th)

                        var recommendMovieInfo = mutableListOf<movieInfoItem>()

                        for (title in result) {
                            val retrofit = Retrofit.Builder()
                                    .baseUrl("https://434063da14e9.ap.ngrok.io")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                            val useInterface = retrofit.create(searchMovies::class.java)

                            useInterface.titles(title).enqueue(object : Callback<List<movieInfoItem>> {
                                override fun onFailure(call: Call<List<movieInfoItem>>, t: Throwable) {
                                    val message = "네트워크가 원할하지 않습니다. ㅠㅠ"
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                }

                                override fun onResponse(call: Call<List<movieInfoItem>>, response: Response<List<movieInfoItem>>) {
                                    val titleList = response.body() as List<movieInfoItem>
                                    recommendMovieInfo.add(titleList.get(0))

                                    // 리사이클러뷰
                                    val data: MutableList<movieInfoItem> = recommendMovieInfo
                                    var adapter = CustomAdapter_choose()
                                    adapter.listData = data
                                    view.recyclerView.adapter = adapter
                                    view.recyclerView.layoutManager = LinearLayoutManager(context)
                                }
                            })
                        }
                    }
                })
            } else {
                Log.d("not data", "not data in here 2222")
            }
        }
        return view
    }
    override fun onResume() {
        super.onResume()
        // 최근 리뷰한 영화 보여주기
        thread(start=true) {
            val helper = SqliteHelper(activity, "review", 1)
            Log.d("select", "${helper.selectReviewedMovieReverce()} 333333")
            // 추천 영화 restAPI로 검색하기
            if (helper.selectReviewedMovieReverce().size != 0) {
                val retrofit = Retrofit.Builder()
                        .baseUrl("https://434063da14e9.ap.ngrok.io")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                val useInterface = retrofit.create(searchRecommend::class.java)
                Log.d("qwertyqwrety", "${helper.selectReviewedMovieReverce().get(0)}")
                useInterface.titles(helper.selectReviewedMovieReverce().get(0).title.toString()).enqueue(object : Callback<List<getRecommendItem>> {
                    // 네트워크가 불안정할 때
                    override fun onFailure(call: Call<List<getRecommendItem>>, t: Throwable) {
                        val message = "네트워크가 원할하지 않습니다."
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    }

                    // 네트워크가 잘 될 때
                    override fun onResponse(call: Call<List<getRecommendItem>>, response: Response<List<getRecommendItem>>) {
                        // 우선 추천 목록을 받아서
                        var titleList = response.body() as List<getRecommendItem>
                        var recommendList = titleList.get(0)

                        // 추천 영화 제목들을 리스트로 저장 (밑에서 반복문으로 활용)
                        var result = mutableListOf<String>()
                        result.add(recommendList.movie_1st)
                        result.add(recommendList.movie_2nd)
                        result.add(recommendList.movie_3rd)
                        result.add(recommendList.movie_4th)
                        result.add(recommendList.movie_5th)
                        result.add(recommendList.movie_6th)
                        result.add(recommendList.movie_7th)
                        result.add(recommendList.movie_8th)
                        result.add(recommendList.movie_9th)
                        result.add(recommendList.movie_10th)

                        var recommendMovieInfo = mutableListOf<movieInfoItem>()

                        for (title in result) {
                            val retrofit = Retrofit.Builder()
                                    .baseUrl("https://434063da14e9.ap.ngrok.io")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                            val useInterface = retrofit.create(MainActivity.searchMovies::class.java)

                            useInterface.titles(title).enqueue(object : Callback<List<movieInfoItem>> {
                                override fun onFailure(call: Call<List<movieInfoItem>>, t: Throwable) {
                                    val message = "네트워크가 원할하지 않습니다. ㅠㅠ"
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                }

                                override fun onResponse(call: Call<List<movieInfoItem>>, response: Response<List<movieInfoItem>>) {
                                    val titleList = response.body() as List<movieInfoItem>
                                    recommendMovieInfo.add(titleList.get(0))

                                    // 리사이클러뷰
                                    val data: MutableList<movieInfoItem> = recommendMovieInfo
                                    var adapter = CustomAdapter_choose()
                                    adapter.listData = data
                                    recyclerView.adapter = adapter
                                    recyclerView.layoutManager = LinearLayoutManager(context)
                                }
                            })
                        }
                    }
                })
            } else {
                Log.d("not data", "not data in here 4444")
            }
        }
    }
    // 네트워크 연결을 위한 인터페이스
    interface searchRecommend {
        @GET("api/recommend/{title}")
        fun titles(@Path("title") title: String): Call<List<getRecommendItem>>
    }

    interface searchMovies {
        @GET("api/movie/{title}")
        fun titles(@Path("title") title: String): Call<List<movieInfoItem>>
    }
}