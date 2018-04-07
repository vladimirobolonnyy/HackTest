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
        const val TAG = "NewParticipantFragment"
    }

    lateinit var presenter: MainPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_new_participant, container, false)
        val addParticipantButton = view.findViewById<Button>(R.id.addNewParticipantButton)
        addParticipantButton.setOnClickListener({
            addNewParticipant(view)
        })
        val attachPhotoButton = view.findViewById<Button>(R.id.attachPhoto)
        addParticipantButton.setOnClickListener({
            attachPhoto()
        })
        return view
    }

    private fun attachPhoto(){

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
        presenter.addParticipant(participant)
    }
}