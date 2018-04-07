package com.obolonnyy.hacktest

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


/**
 * Created by Vladimir Obolonnyy on 02.04.2018.
 */
class ParticipantInfoFragment : Fragment() {

    // Тут карточка отображения одного участника

    lateinit var presenter: MainPresenter
    lateinit var participant: Participant

    companion object {
        val TAG = "ParticipantInfoFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_participant_info, container, false)

        val imageView = view.findViewById<ImageView>(R.id.photo)
        imageView.setImageURI(Uri.parse(participant.photopath))

        val firstName = view.findViewById<TextView>(R.id.firstName)
        firstName?.text = participant.firstName

        val lastName = view.findViewById<TextView>(R.id.lastName)
        lastName?.text = participant.lastName

        val middleName = view.findViewById<TextView>(R.id.middleName)
        middleName?.text = participant.middleName

        val group = view.findViewById<TextView>(R.id.group)
        group?.text = participant.group

        val description = view.findViewById<TextView>(R.id.description)
        description?.text = participant.description

        val delButton = view.findViewById<Button>(R.id.removeActionButton)
        delButton.setOnClickListener({
            presenter.removeParticipant(participant)
        })

        return view
    }
}