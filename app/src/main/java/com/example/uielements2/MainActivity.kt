package com.example.uielements2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private fun append(arr: Array<String>, element: String): Array<String>{
        val list: MutableList<String> = arr.toMutableList()
        list.add(element)
        return list.toTypedArray()
    }

    companion object{
        var songQueue: Array<String> = emptyArray()
        val songsArray = arrayOf("Blood","Bulletproof Heart","Cancer","Cemetery Drive","Dead!","Destroya","Disenchanted"
                ,"Famous Last Words","Give ‘Em Hell, Kid","Goodnite, Dr. Death","Hang ‘Em High","Helena"
                ,"House of Wolves","I Don’t Love You","I Never Told You What I Do for a Living","Interlude",
                "It’s Not a Fashion Statement, It’s a Deathwish","I’m Not Okay (I Promise)","Jet-Star and the Kobra Kid/Traffic Report",
                "Look Alive, Sunshine","Mama","Na Na Na (Na Na Na Na Na Na Na Na Na)","Planetary (Go!)","S/C/A/R/E/C/R/O/W",
                "Save Yourself, I’ll Hold Them Back", "Sing","Sleep","Summertime","Teenagers","Thank You for the Venom","The End",
                "The Ghost of You","The Jetset Life is Gonna Kill You","The Kids from Yesterday","The Only Hope for Me is You",
                "The Sharpest Lives","This Is How I Disappear","To the End","Vampire Money","Welcome to the Black Parade",
                "You Know What They Do to Guys Like Us in Prison")
        val blackparade = arrayOf("To the End","Dead!","This Is How I Disappear","The Sharpest Lives",
                "Welcome to the Black Parade","I Don’t Love You","House of Wolves","Cancer","Mama","Sleep",
                "Teenagers","Disenchanted","Famous Last Words","Blood")
        val sweetrevenge = arrayOf("Helena","Give ‘Em Hell, Kid","To the End","You Know What They Do to Guys Like Us in Prison",
                "I’m Not Okay (I Promise)","The Ghost of You","The Jetset Life is Gonna Kill You","Interlude",
                "Thank You for the Venom","Hang ‘Em High","It’s Not a Fashion Statement, It’s a Deathwish",
                "Cemetery Drive","I Never Told You What I Do for a Living")
        val dangerdays = arrayOf("Look Alive, Sunshine","Na Na Na (Na Na Na Na Na Na Na Na Na)",
                "Bulletproof Heart","Sing","Planetary (Go!)","The Only Hope for Me is You",
                "Jet-Star and the Kobra Kid/Traffic Report","Party Poison","Save Yourself, I’ll Hold Them Back",
                "S/C/A/R/E/C/R/O/W","Summertime","Destroya","The Kids from Yesterday","Goodnite, Dr. Death",
                "Vampire Money")

        val albumArray = arrayOf("The Black Parade","Three Cheers for Sweet Revenge","Danger Days: The True Lives of the Fabulous Killjoys")

        val albumPics = arrayOf(R.drawable.black_parade,R.drawable.sweet_revenge,R.drawable.danger_days)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter<String>(this, R.layout.textcolor, songsArray)
        val songList = findViewById<ListView>(R.id.songList)
        songList.adapter = adapter
        registerForContextMenu(songList)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.item_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId){
            R.id.add_to_queue ->{
                songQueue = append(songQueue, songsArray[info.position])
                Toast.makeText(this, "Song added to Playlist", Toast.LENGTH_LONG).show()
                return true
            }
            else -> super.onContextItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.queue ->{
                startActivity(Intent(this, QueueActivity::class.java))
                true
            }
            R.id.songs ->{
                true
            }
            R.id.album ->{
                startActivity(Intent(this, AlbumActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}