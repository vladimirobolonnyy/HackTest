package com.obolonnyy.hacktest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerView.adapter = ParticipantsAdapter(generateUs())
    }

    fun generateUs(): ArrayList<Participant> {
        val result = ArrayList<Participant>()
        for (i in 0..5){
            result.add(Participant("${i}", "${i+10}"))
            println(i)
        }
        return result
    }
}
