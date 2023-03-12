package com.example.activitybasic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.activitybasic.R.id.to1
import com.example.activitybasic.R.id.to2

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        val to1 = findViewById<TextView>(to1)
        val to2 = findViewById<TextView>(to2)

        to1.text = intent.getStringExtra("from1")
        to2.text = "${intent.getIntExtra("from2", 0)}"


        val btnClose = findViewById<Button>(R.id.btnClose)
        btnClose.setOnClickListener {
            val returnIntent = Intent()
            val editMessage = findViewById<EditText>(R.id.editMessage)
            returnIntent.putExtra("returnValue", editMessage.text.toString())
            setResult(RESULT_OK, returnIntent)
            finish()
        }
    }
}
