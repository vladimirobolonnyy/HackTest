package com.obolonnyy.hacktest

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        MainView, ParticipantsAdapter.OnItemClicker {

    private val presenter by lazy { MainPresenter(this) }
    private lateinit var adapter: ParticipantsAdapter
    private lateinit var addButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ParticipantDatabaseService.init(this)
        ParticipantDatabaseService.prepopulateParticipantsIfFirstRun(this)
        setContentView(R.layout.activity_main)
        addButton = this.findViewById(R.id.floatingActionButton)
        presenter.initAllItems()
    }

    override fun initRecyclerView(elements: List<Participant>) {
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerView.adapter = ParticipantsAdapter(elements, this)
    }

    override fun onPersonClick(participant: Participant) {
        addButton.visibility = View.GONE
        val fragment = ParticipantInfoFragment()
        fragment.presenter = this.presenter
        fragment.participant = participant
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
        transaction.add(R.id.main_container, fragment, ParticipantInfoFragment.TAG)
        transaction.addToBackStack(ParticipantInfoFragment.TAG)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            val fragment = supportFragmentManager.findFragmentByTag(ParticipantInfoFragment.TAG)
            if (fragment != null){
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
                transaction.remove(fragment)
                transaction.commit()
                supportFragmentManager.popBackStack()
                addButton.visibility = View.VISIBLE
            }
        } else {
            super.onBackPressed()
        }
    }

    fun openNewParticipantForm(view: View) {
        val fragment = NewParticipantFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.main_container, fragment, NewParticipantFragment.TAG)
        transaction.addToBackStack(NewParticipantFragment.TAG)
        transaction.commit()
    }
}
