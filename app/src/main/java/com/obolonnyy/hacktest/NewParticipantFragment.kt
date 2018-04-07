package com.obolonnyy.hacktest

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_new_participant.*

/**
 * Фрагмент создания нового участника
 *
 * Created by Vladimir Obolonnyy on 03.04.2018.
 */
class NewParticipantFragment : Fragment() {

    companion object {
        const val TAG = "NewParticipantFragment"
        val GALLERY = 1
    }

    lateinit var presenter: MainPresenter
    private val newParticipant = Participant()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_participant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNewParticipantButton.setOnClickListener({ addNewParticipant() })
        attachPhoto.setOnClickListener({ attachPhoto() })
    }

    private fun attachPhoto() {
        selectImage()
    }

    private fun selectImage() {
        val items = arrayOf<CharSequence>("Выбрать из галереи", "Отмена")
        val builder = AlertDialog.Builder(this.context)
        builder.setTitle("Добавить фото")
        builder.setItems(items, DialogInterface.OnClickListener { dialog, item ->
            when (items[item]) {
                "Выбрать из галереи" -> {
                    if ((this.activity!! as MainActivity).checkPermissions()) {
                        val intent = Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(intent, GALLERY)
                    }
                }
                "Отмена" -> dialog.dismiss()
                else -> {
                }
            }
        })
        builder.show()
    }

    private fun addNewParticipant() {
        // Теперь все вьюхи автоматом подкачиваются благодаря
        // kotlinx.android.synthetic.main.fragment_new_participant.*
        newParticipant.apply {
            lastName = lastNameInput.text.toString()
            firstName = firstNameInput.text.toString()
            middleName = middleNameInput.text.toString()
            group = groupInput.text.toString()
            description = descriptionInput.text.toString()
        }
        presenter.addParticipant(newParticipant)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == GALLERY && resultCode == RESULT_OK && null != data) {
                val uri = data.data
                newParticipant.photopath = uri.toString()
                val photoName = uri.path.split("/").get(uri.path.split("/").lastIndex)
                attachPhoto.text = "Выбранное фото: $photoName"
            }
        } catch (e: Exception) {
            Toast.makeText(this.context, "Please try again", Toast.LENGTH_LONG).show()
        }
    }
}