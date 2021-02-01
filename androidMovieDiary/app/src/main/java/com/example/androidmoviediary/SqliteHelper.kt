package com.example.androidmoviediary

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SqliteHelper(context: Context?, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val create = "create table recommendedMovie(num integer primary key, title text, rating text, genre text, year integer, plot text, unique(title))"
        db?.execSQL(create)
    }

    fun insertRecommendedMovie(movie: Movie) {
        val values = ContentValues()
        values.put("title", movie.title)
        values.put("rating", movie.rating)
        values.put("genre", movie.genre)
        values.put("year", movie.year)
        values.put("plot", movie.plot)

        val wd = writableDatabase
        wd.insert("recommendedMovie", null, values)
        wd.close()
    }

    fun deleteRecommendedMovie(movie : Movie) {
        val delete = "delete from recommendedMovie where title = '${movie.title}'"
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

            list.add(Movie(title, rating, genre, year, plot))
        }

        cursor.close()
        rd.close()

        return list
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}