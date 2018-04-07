package com.obolonnyy.hacktest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

/**
 * Created by Vladimir Obolonnyy on 03.04.2018.
 */
class NewParticipantFragment : Fragment() {

    // Это карточка создания нового участника

    companion object {
        val TAG = "NewParticipantFragment"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.new_participant_fragment, container, false)
        val addParticipantButton = view.findViewById<Button>(R.id.addNewParticipantButton)
        addParticipantButton.setOnClickListener({
            _ -> addNewParticipant(view)
        })
        return view
    }


    private fun addNewParticipant(view: View) {
        val lastNameInput = view.findViewById<EditText>(R.id.lastNameInput)
        val firstNameInput = view.findViewById<EditText>(R.id.firstNameInput)
        val middleNameInput = view.findViewById<EditText>(R.id.middleNameInput)
        val groupInput = view.findViewById<EditText>(R.id.groupInput)
        val descriptionInput = view.findViewById<EditText>(R.id.descriptionInput)

        val participant = Participant(
                lastName = lastNameInput.text.toString(),
                firstName = firstNameInput.text.toString(),
                middleName = middleNameInput.text.toString(),
                group = groupInput.text.toString(),
                description = descriptionInput.text.toString()
        )
        ParticipantDatabaseService.addParticipant(participant)

        activity!!.supportFragmentManager.popBackStackImmediate()
    }
}