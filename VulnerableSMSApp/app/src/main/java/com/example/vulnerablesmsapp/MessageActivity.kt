package com.example.vulnerablesmsapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : AppCompatActivity() {
    private var id = "";
    private var number = "";
    private lateinit var recycleViewAdapter: MessageRecycleViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        name_text_view.text = intent.extras?.get("name").toString()
        id = intent.extras?.get("id").toString();
        number = intent.extras?.get("number").toString();
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this.context)
            recycleViewAdapter = MessageRecycleViewAdapter()
            adapter = recycleViewAdapter
        }
        getMessages();
    }

    private fun getMessages() {
        val list = ArrayList<MessageData>()
        val urlMessage = "content://com.example.vulnerablesmsapp.SMSContentProvider/messages/$id"
        val messages = Uri.parse(urlMessage)
        val cursorMessage = this.contentResolver?.query(messages, null, null, null, SMSContentProvider.TIMESTAMP)

        if (cursorMessage != null) {
            if (cursorMessage.moveToFirst()) {
                do {
                    list.add(
                            MessageData(
                                    cursorMessage.getString(3).toBoolean(),
                                    cursorMessage.getString(2)
                            )
                    )
                } while (cursorMessage.moveToNext())
            }
        }
        cursorMessage?.close()
        recycleViewAdapter.submitList(list)
        recycleViewAdapter.notifyDataSetChanged()
    }
}