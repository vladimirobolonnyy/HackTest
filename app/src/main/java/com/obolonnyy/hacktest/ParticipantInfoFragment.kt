package com.obolonnyy.hacktest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


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
        return inflater.inflate(R.layout.participant_fragment, container, false);
    }
}