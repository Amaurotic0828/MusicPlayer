package com.example.uielements2

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class AlbumDetails : AppCompatActivity() {
    private lateinit var songtoalbumListView: Array<String>
    private lateinit var adapter: ArrayAdapter<String>
    private fun remove(arr: Array<String>, element: Int): Array<String> {
        if (element < 0 || element >= arr.size) {
            return arr
        }
        val result = arr.toMutableList()
        result.removeAt(element)
        return result.toTypedArray()
    }

    override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.album_menu, menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)
        val bundle = intent.extras
        var arraySongs: Array<String> = emptyArray()
        if (bundle != null) {
            when (bundle.getInt("position")) {
                0 -> arraySongs = MainActivity.albumsongslist[0]
                1 -> arraySongs = MainActivity.albumsongslist[1]
                2 -> arraySongs = MainActivity.albumsongslist[2]
            }
        }
        findViewById<ImageView>(R.id.albumImageView).setImageResource(MainActivity.albumPics[bundle?.getInt("position")!!])
        findViewById<TextView>(R.id.albumTextView).setText(bundle.getString("name"))
        songtoalbumListView = MainActivity.albumsongslist[bundle?.getInt("position")!!]
        val adapter = ArrayAdapter<String>(this, R.layout.textcolor, arraySongs)
        val songList = findViewById<ListView>(R.id.albumListView)
        songList.adapter = adapter
        registerForContextMenu(songList)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val bundle = intent.extras
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.remove_from_albums -> {
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setMessage("Are you sure you want to remove this song from the album ${bundle?.getString("name")}?")
                        .setCancelable(false)
                        .setPositiveButton("CONFIRM", DialogInterface.OnClickListener { _, _ ->
                            MainActivity.albumsongslist[bundle?.getInt("position")!!] = remove(MainActivity.albumsongslist[bundle.getInt("position")], info.position)
                            songtoalbumListView = MainActivity.albumsongslist[bundle.getInt("position")]
                            adapter = ArrayAdapter<String>(this, R.layout.textcolor, songtoalbumListView)
                            val albumsongs = findViewById<ListView>(R.id.albumListView)
                            albumsongs.adapter = adapter
                        })
                        .setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialog, _ ->
                            dialog.cancel()
                        })
                val alert = dialogBuilder.create()
                alert.setTitle("Remove from albums")
                alert.show()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}