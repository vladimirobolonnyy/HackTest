package com.obolonnyy.hacktest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        MainView, ParticipantsAdapter.OnItemClicker {

    private val presenter by lazy { MainPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.initAllItems()
    }

    public fun addNewParticipant(view: View) {
/*        val fragment = NewParticipantFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_container, fragment, NewParticipantFragment.TAG)
        transaction.addToBackStack(NewParticipantFragment.TAG)
        transaction.commit()*/
        Toast.makeText( this, "Clicked", Toast.LENGTH_SHORT).show()
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
        transaction.addToBackStack(ParticipantInfoFragment.TAG)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
