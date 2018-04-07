package com.obolonnyy.hacktest

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        MainView, ParticipantsAdapter.OnItemClicker {

    private val presenter by lazy { MainPresenter(this) }
    private lateinit var addButton: FloatingActionButton
    private val animationDuration = 300L
    private val PICK_FROM_GALLERY: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ParticipantDatabaseService.init(this)
        ParticipantDatabaseService.prepopulateParticipantsIfFirstRun(this)
        setContentView(R.layout.activity_main)
        addButton = this.findViewById(R.id.floatingActionButton)
        presenter.initMainAdapterItems()
        checkPermissions()
    }

    override fun initRecyclerView(elements: List<Participant>) {
        mainRecyclerView.layoutManager = LinearLayoutManager(this)
        mainRecyclerView.adapter = ParticipantsAdapter(elements, this)
    }

    override fun onPersonClick(participant: Participant) {
        (mainRecyclerView.adapter as ParticipantsAdapter).userCanClick = false
        hideAddButtonWithAnimation()
        val fragment = ParticipantInfoFragment()
        fragment.presenter = this.presenter
        fragment.participant = participant
        startFragmentWithAnimation(fragment)
    }

    override fun makeToast(text: String, lengt: Int) {
        Toast.makeText(this.baseContext, text, lengt).show()
    }

    override fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(addButton.getWindowToken(), 0)
    }

    fun openNewParticipantForm(view: View) {
        hideAddButtonWithAnimation()
        val fragment = NewParticipantFragment()
        fragment.presenter = presenter
        startFragmentWithAnimation(fragment)
    }

    private fun startFragmentWithAnimation(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
        transaction.add(R.id.main_container, fragment, NewParticipantFragment.TAG)
        transaction.addToBackStack(NewParticipantFragment.TAG)
        transaction.commit()
    }

    private fun hideAddButtonWithAnimation() {
        addButton.isClickable = false
        addButton.setAlpha(1.0f)
        addButton.animate()
                .setDuration(animationDuration)
                .alpha(0.0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        addButton.setVisibility(View.INVISIBLE)
                    }
                })
    }

    private fun showAddButtonWithAnimation() {
        addButton.isClickable = true
        addButton.setAlpha(0.0f)
        addButton.animate()
                .setDuration(animationDuration)
                .alpha(1.0f)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        super.onAnimationEnd(animation)
                        addButton.setVisibility(View.VISIBLE)
                    }
                })
    }

    override fun onBackPressed() {
        val activeFragment = getVisibleFragment()
        if (activeFragment != null) {
            showAddButtonWithAnimation()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
            transaction.remove(activeFragment)
            transaction.commit()
            supportFragmentManager.popBackStack()
            (mainRecyclerView.adapter as ParticipantsAdapter).userCanClick = true
        } else {
            super.onBackPressed()
        }
    }

    private fun getVisibleFragment(): Fragment? {
        val fragments = supportFragmentManager.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible)
                    return fragment
            }
        }
        return null
    }

    fun checkPermissions(): Boolean {
        return if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PICK_FROM_GALLERY)
            false
        } else {
            true
        }
    }
}
