package com.obolonnyy.hacktest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        MainView, ParticipantsAdapter.OnItemClicker {

    private val presenter by lazy { MainPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.initAllItems()
    }

    override fun initRecyclerView(elements: List<Participant>) {
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerView.adapter = ParticipantsAdapter(elements, this)
    }

    override fun onPersonClick(participant: Participant) {
        val fragment = ParticipantInfoFragment()
        fragment.participant = participant
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_container, fragment, ParticipantInfoFragment.TAG)
        transaction.commit()
    }
}
