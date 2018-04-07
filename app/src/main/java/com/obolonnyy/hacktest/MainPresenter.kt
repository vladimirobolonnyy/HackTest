package com.obolonnyy.hacktest

import android.widget.Toast

/**
 * Created by Vladimir Obolonnyy on 02.04.2018.
 */
class MainPresenter(val view: MainView) {

    // Тут должна быть вся логика работы приложения

    companion object {
        const val fielsError = "Заполните как минимум имя и фамилию участника"
    }

    fun initMainAdapterItems() {
        view.initRecyclerView(ParticipantDatabaseService.getAllParticipants())
    }

    fun removeParticipant(participant: Participant) {
        val name = participant.firstName
        val lastName = participant.lastName
        ParticipantDatabaseService.removeParticipant(participant)
        view.makeToast("$name $lastName удалён.", Toast.LENGTH_SHORT)
        initMainAdapterItems()
        view.onBackPressed()
    }

    fun addParticipant(participant: Participant) {
        if (participant.firstName.isNotEmpty() && participant.lastName.isNotEmpty()) {
            view.hideKeyboard()
            ParticipantDatabaseService.addParticipant(participant)
            view.makeToast("${participant.firstName} ${participant.lastName} добавлен.", Toast.LENGTH_SHORT)
            initMainAdapterItems()
            view.onBackPressed()
        } else {
            view.makeToast(fielsError, Toast.LENGTH_SHORT)
        }
    }
}