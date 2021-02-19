package com.example.androidmoviediary

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.core.content.contentValuesOf

class SqliteHelper(context: Context?, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createRecommendedMovie = "create table recommendedMovie(num integer primary key, title text, rating text, genre text, year integer, plot text, img_url text, unique(title))"
        val createReviewedMovie = "create table reviewedMovie(num integer primary key, year integer, month integer, day integer, title text, review text, rating text, genre text, movieYear integer, img_url text, unique(review))"

        db?.execSQL(createRecommendedMovie)
        db?.execSQL(createReviewedMovie)
    }

    fun insertRecommendedMovie(movie: Movie) {
        val values = ContentValues()
        values.put("title", movie.title)
        values.put("rating", movie.rating)
        values.put("genre", movie.genre)
        values.put("year", movie.year)
        values.put("plot", movie.plot)
        values.put("img_url", movie.img_url)

        val wd = writableDatabase
        wd.insert("recommendedMovie", null, values)
        wd.close()
    }

    fun insertReviewedMovie(review: Review): Boolean {
        val values = ContentValues()
        values.put("year", review.year)
        values.put("month", review.month)
        values.put("day", review.day)
        values.put("title", review.title)
        values.put("review", review.review)
        values.put("rating", review.rating)
        values.put("genre", review.genre)
        values.put("movieYear", review.movieYear)
        values.put("img_url", review.img_url)

        val wd = writableDatabase

        if (review.title.isEmpty()) {
            wd.close()
            return false
        } else if (review.review.isEmpty()) {
            wd.close()
            return false
        } else {
            wd.insert("ReviewedMovie", null, values)
            wd.close()
            return true
        }
    }


    fun deleteRecommendedMovie(movie: Movie) {
        val delete = "delete from recommendedMovie where title = '${movie.title}'"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()
    }

    fun deleteReviewedMovie(review: Review) {
        val delete = "delete from reviewedMovie where review = '${review.review}'"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()
    }


    fun selectRecommendedMovie() : MutableList<Movie> {
        val list = mutableListOf<Movie>()
        val select = "select * from recommendedMovie"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select, null)

        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val rating = cursor.getString(cursor.getColumnIndex("rating"))
            val genre = cursor.getString(cursor.getColumnIndex("genre"))
            val year = cursor.getInt(cursor.getColumnIndex("year"))
            val plot = cursor.getString(cursor.getColumnIndex("plot"))
            val img_url = cursor.getString(cursor.getColumnIndex("img_url"))

            list.add(Movie(title, rating, genre, year, plot, img_url))
        }

        cursor.close()
        rd.close()

        return list
    }

    fun selectReviewedMovie() : MutableList<Review> {
        val list = mutableListOf<Review>()
        val select = "select * from reviewedMovie"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select, null)

        while (cursor.moveToNext()) {
            val year = cursor.getInt(cursor.getColumnIndex("year"))
            val month = cursor.getInt(cursor.getColumnIndex("month"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val review = cursor.getString(cursor.getColumnIndex("review"))
            val rating = cursor.getString(cursor.getColumnIndex("rating"))
            val genre = cursor.getString(cursor.getColumnIndex("genre"))
            val movieYear = cursor.getString(cursor.getColumnIndex("movieYear"))
            val img_url = cursor.getString(cursor.getColumnIndex("img_url"))

            list.add(Review(year, month, day, title, review, rating, genre, movieYear, img_url))
        }

        cursor.close()
        rd.close()

        return list
    }

    fun selectReviewedMovieReverce() : MutableList<Review> {
        val list = mutableListOf<Review>()
        val select = "select * from reviewedMovie order by num desc"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select, null)

        while (cursor.moveToNext()) {
            val year = cursor.getInt(cursor.getColumnIndex("year"))
            val month = cursor.getInt(cursor.getColumnIndex("month"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val review = cursor.getString(cursor.getColumnIndex("review"))
            val rating = cursor.getString(cursor.getColumnIndex("rating"))
            val genre = cursor.getString(cursor.getColumnIndex("genre"))
            val movieYear = cursor.getString(cursor.getColumnIndex("movieYear"))
            val img_url = cursor.getString(cursor.getColumnIndex("img_url"))

            list.add(Review(year, month, day, title, review, rating, genre, movieYear, img_url))
        }

        cursor.close()
        rd.close()

        return list
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}