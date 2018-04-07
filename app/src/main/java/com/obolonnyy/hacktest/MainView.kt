package com.obolonnyy.hacktest

/**
 * Created by Vladimir Obolonnyy on 02.04.2018.
 */
interface MainView {
    fun initRecyclerView(elements: List<Participant>)
    fun onBackPressed()
    fun makeToast(text: String, lengt: Int)
    fun hideKeyboard()
}