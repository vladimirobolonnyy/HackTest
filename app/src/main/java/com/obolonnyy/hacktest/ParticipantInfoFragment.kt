package com.obolonnyy.hacktest

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


/**
 * Created by Vladimir Obolonnyy on 02.04.2018.
 */
class ParticipantInfoFragment : Fragment() {

    // Тут должна быть карточка одного участника

    lateinit var participant: Participant

    companion object {
        val TAG = "ParticipantInfoFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.participant_fragment, container, false)

        val imageView = view.findViewById<ImageView>(R.id.photo)
        imageView.setImageDrawable(Drawable.createFromPath("image/i.jpg"))

        val firstName = view.findViewById<TextView>(R.id.firstName)
        firstName?.text = participant.firstName

        val lastName = view.findViewById<TextView>(R.id.lastName)
        lastName?.text = participant.lastName

        val middleName = view.findViewById<TextView>(R.id.middleName)
        middleName?.text = participant.middleName

        val sex = view.findViewById<TextView>(R.id.sex)
        sex?.text = participant.sex

        val group = view.findViewById<TextView>(R.id.group)
        group?.text = participant.group

        val description = view.findViewById<TextView>(R.id.description)
        description?.text = participant.description

        return view
    }
}