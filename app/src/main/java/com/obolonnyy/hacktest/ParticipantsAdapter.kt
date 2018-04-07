package com.obolonnyy.hacktest

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by Vova on 01.04.2018.
 */
class ParticipantsAdapter(var elements: List<Participant>,
                          private val onItemClicker: OnItemClicker) :
        Adapter<ParticipantsAdapter.ParticipantViewHolder>() {

    var userCanClick: Boolean = true

    interface OnItemClicker {
        fun onPersonClick(participant: Participant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_person, parent, false)
        return ParticipantViewHolder(view)
    }

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        holder.fio.text = "${elements[position].firstName} ${elements[position].lastName}"
        holder.root.setOnClickListener {
            if (userCanClick){
                onItemClicker.onPersonClick(elements[position])
            }
        }
    }

    inner class ParticipantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view
        val fio = view.findViewById<TextView>(R.id.personTextView)
    }
}