package com.example.uielements2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class AlbumDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)


        val bundle = intent.extras
        var arraySongs: Array<String> = emptyArray()
        if (bundle != null) {
            when (bundle.getInt("position")){
                0 -> arraySongs = MainActivity.blackparade
                1 -> arraySongs = MainActivity.sweetrevenge
                2 -> arraySongs = MainActivity.dangerdays
            }
        }
        findViewById<ImageView>(R.id.albumImg).setImageResource(MainActivity.albumPics[bundle?.getInt("position")!!])
        findViewById<TextView>(R.id.albumNameTxt).setText(bundle.getString("name"))

        val adapter = ArrayAdapter<String>(this, R.layout.textcolor, arraySongs)
        val songList = findViewById<ListView>(R.id.albumSongList)
        songList.adapter = adapter
    }
}