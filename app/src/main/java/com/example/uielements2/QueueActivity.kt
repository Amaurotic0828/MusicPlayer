package com.example.uielements2

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class QueueActivity : AppCompatActivity() {
    override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.queue_details_menu, menu)
    }

    private lateinit var notificationMiniger: NotificationManager
    private lateinit var notificationCh: NotificationChannel
    private lateinit var builder: Notification.Builder
    private val channelNumber = "i.apps.notifications"
    private val notificationdetails = "Test notification"

    private fun remove(list: Array<String>, element: Int): Array<String> {
        if (element < 0 || element >= list.size) {
            return list
        }
        val list = list.toMutableList()
        list.removeAt(element)
        return list.toTypedArray()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        notificationMiniger = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queue)

        val adapter = ArrayAdapter<String>(this, R.layout.textcolor, MainActivity.songQueue)
        val queueList = findViewById<ListView>(R.id.queueListView)
        queueList.adapter = adapter
        registerForContextMenu(queueList)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.remove -> {
                MainActivity.songQueue = remove(MainActivity.songQueue, info.position)
                var adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.textcolor, MainActivity.songQueue)
                adapter = ArrayAdapter<String>(this, R.layout.textcolor, MainActivity.songQueue)
                val newQueue = findViewById<ListView>(R.id.queueListView)
                newQueue.adapter = adapter
                Toast.makeText(this, "Song removed from Playlist!", Toast.LENGTH_SHORT).show()
                if (MainActivity.songQueue.isEmpty()) {
                    val intent = Intent(this, MainActivity::class.java)
                    val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        notificationCh = NotificationChannel(
                                channelNumber, notificationdetails, NotificationManager.IMPORTANCE_HIGH)
                        notificationCh.enableLights(true)
                        notificationCh.lightColor = Color.BLACK
                        notificationCh.enableVibration(true)
                        notificationMiniger.createNotificationChannel(notificationCh)

                        builder = Notification.Builder(this, channelNumber).setContentTitle("QUEUE EMPTY").setContentText("Add Songs to your Playlist!")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentIntent(pendingIntent)
                    } else {
                        builder = Notification.Builder(this)
                                .setContentTitle("Notifications Example")
                                .setContentText("This is a notification message")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentIntent(pendingIntent)
                    }
                    notificationMiniger.notify(1234, builder.build())
                }
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}