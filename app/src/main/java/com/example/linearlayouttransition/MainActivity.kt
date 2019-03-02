package com.example.linearlayouttransition

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        textView.setOnClickListener {
//            linearLayout.removeView(linearLayout.getChildAt(0))
//        }
        addTextView.setOnClickListener {
            constraintLayout.addView(makeView())
        }
        removeTextView.setOnClickListener {
            constraintLayout.removeView(constraintLayout.getChildAt(0))
        }
    }

    private fun makeView(): View {
        val newView = LayoutInflater.from(this).inflate(R.layout.new_view, constraintLayout, false)
        val rnd = Random()
        val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        newView.setBackgroundColor(color)
        return newView
    }
}
