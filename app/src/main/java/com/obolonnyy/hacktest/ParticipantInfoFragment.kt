package com.obolonnyy.hacktest

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_participant_info.*


/**
 * Created by Vladimir Obolonnyy on 02.04.2018.
 */
class ParticipantInfoFragment : Fragment() {

    // Тут карточка отображения одного участника

    lateinit var presenter: MainPresenter
    lateinit var participant: Participant

    companion object {
        val TAG = "ParticipantInfoFragment"
        val maxHeight = 400
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_participant_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Теперь все вьюхи автоматом подкачиваются благодаря
        // kotlinx.android.synthetic.main.fragment_new_participant.*

        if (participant.photopath.isNotEmpty()) {
            val bitmap = uriToScaledPhoto(participant.photopath)
            photo.setImageBitmap(bitmap)
        }

        firstName.text = participant.firstName
        lastName.text = participant.lastName
        middleName.text = participant.middleName
        group.text = participant.group
        description.text = participant.description
        removeActionButton.setOnClickListener({
            presenter.removeParticipant(participant)
        })
    }

    private fun uriToScaledPhoto(stringUri: String): Bitmap {
        val uri = Uri.parse(stringUri)
        var bitmap = MediaStore.Images.Media.getBitmap(this.context!!.contentResolver, uri)
        val aspectRatio = bitmap.height.toFloat() / bitmap.width.toFloat()
        val width = Math.round(maxHeight / aspectRatio)
        bitmap = Bitmap.createScaledBitmap(
                bitmap, width, maxHeight, true)
        return bitmap
    }
}